package com.ang.rest.product;

import com.ang.rest.category.CategoryServiceImpl;
import com.ang.rest.domain.dto.ProductDTO;
import com.ang.rest.domain.entity.Category;
import com.ang.rest.domain.entity.Product;
import com.ang.rest.exception.ResourceConflictException;
import com.ang.rest.exception.ResourceNotFoundException;
import com.ang.rest.mapper.impl.ProductMapper;
import com.ang.rest.transaction_details.TransactionDetailsRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class ProductServiceImpl implements ProductService {
    @Inject
    TransactionDetailsRepository transactionDetailsRepository;
    @Inject
    ProductRepository productRepository;
    @Inject
    CategoryServiceImpl categoryService;
    @Inject
    ProductMapper productMapper;
    private final String entityName = "product";


    @Transactional
    @Override
    public ProductDTO update(Long id, ProductDTO productDto) {
        Product existingProduct = productRepository.findById(id);
        if (existingProduct == null) throw new ResourceNotFoundException("Product with id " + id + " not found");
        if (!existingProduct.getName().equalsIgnoreCase(productDto.name())) {
            boolean nameExists = productRepository.existsByColumnName(productDto.name());
            if (nameExists) {
                throw new ResourceConflictException("A product with this name already exists.");
            }
        }
        Category category = productDto.categoryName() != null ? categoryService.findCategory(productDto.categoryName()) : existingProduct.getCategory();
        productMapper.updateEntityFromDto(existingProduct, productDto, category);
        return productMapper.mapToDto(existingProduct);
    }

    @Override
    public Product save(ProductDTO productDto) {
        if (productRepository.existsByColumnName(productDto.name())) {
            throw new ResourceConflictException("A product with this name already exists.");
        }
        Category category = categoryService.findByName(productDto.categoryName());
        Product product = productMapper.mapToEntity(productDto, category);
        productRepository.persist(product);
        return product;
    }

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(productMapper::mapToDto).collect(Collectors.toList());
    }


    @Override
    public List<ProductDTO> findAll(String filter, Page page) {
        PanacheQuery<Product> query = productRepository.findByColumnNameIgnoreCase(filter.toLowerCase());
        return query.page(page).stream().map(productMapper::mapToDto).collect(Collectors.toList());

    }

    @Override
    public List<Product> findAllByID(List<Long> productIDList) {
        return productRepository.findAllByIds(productIDList);

    }

    @Override
    @Transactional
    public void delete(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product with id " + productId + " not found.");
        }
        if (transactionDetailsRepository.existsByProduct_id(productId)) {
            throw new ResourceConflictException("This product exists in a transaction. Please remove the product from the associated transaction first.");
        }
        productRepository.deleteById(productId);
    }


    @Override
    public ProductDTO findOne(Long id) {
        return productRepository.findByIdOptional(id).map(productMapper::mapToDto).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));
    }

    @Override
    public Product findOneEntity(Long id) {
        return productRepository.findByIdOptional(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));
    }

}
