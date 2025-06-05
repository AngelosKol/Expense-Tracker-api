package com.ang.rest.product;

import com.ang.rest.category.CategoryServiceImpl;
import com.ang.rest.domain.dto.ProductDTO;
import com.ang.rest.domain.entity.Category;
import com.ang.rest.exception.ResourceNotFoundException;
import com.ang.rest.domain.entity.Product;
import com.ang.rest.mapper.impl.ProductMapper;
import com.ang.rest.transaction_details.TransactionDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final TransactionDetailsRepository transactionDetailsRepository;
    private final CategoryServiceImpl categoryService;
    private final ProductMapper productMapper;




    @Override
    public ProductDTO update(Long id, ProductDTO productDto) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        if (!existingProduct.getName().equalsIgnoreCase(productDto.name())) {
           boolean nameExists = productRepository.existsByName(productDto.name());
           if(nameExists) {
               throw new DataIntegrityViolationException("A product with this name already exists.");
           }
        }
        Category category = productDto.categoryName() != null
                ? categoryService.findCategory(productDto.categoryName())
                : existingProduct.getCategory();
        existingProduct.setName(productDto.name());
        existingProduct.setCategory(category);
        return productMapper.mapToDto(productRepository.save(existingProduct));
    }

    @Override
    public ProductDTO save(ProductDTO productDto) {
        Category category = categoryService.findByName(productDto.categoryName());
        Product product = productMapper.mapToEntity(productDto, category);
        ensureProductNameNotExists(product.getName());
        return productMapper.mapToDto(productRepository.save(product));
    }

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(productMapper::mapToDto).collect(Collectors.toList());
    }


    @Override
    public Page<ProductDTO> findAll(String filter, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(filter,pageable, ProductDTO.class);
    }
    @Override
    public List<Product> findAllByID(List<Long> productIDList) {
        return productRepository.findAllById(productIDList);

    }

    @Override
    @Transactional
    public void delete(Long productId) {
        if(!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product with id " + productId + " not found.");
        }
        if (transactionDetailsRepository.existsByProduct_id(productId)) {
            throw new DataIntegrityViolationException("This product exists in a transaction. Please remove the product from the associated transaction first.");
        }
        productRepository.deleteById(productId);
    }


    @Override
    public ProductDTO findOne(Long id) {
        return productRepository.findById(id)
                .map(productMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));
    }
    @Override
    public Product findOneEntity(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));
    }



    @Override
    public void ensureProductNameNotExists(String name) {
        if (productRepository.existsByName(name)) {
            throw new DataIntegrityViolationException("A product with this name already exists.");
        }
    }


}
