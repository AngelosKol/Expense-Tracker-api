package com.ang.rest.product;

import com.ang.rest.domain.dto.ProductDto;
import com.ang.rest.exceptions.ResourceNotFoundException;
import com.ang.rest.domain.entity.Product;
import com.ang.rest.mappers.impl.ProductMapper;
import com.ang.rest.transaction_details.TransactionDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final TransactionDetailsRepository transactionDetailsRepository;
    private final ProductMapper productMapper;


    @Override
    public void validateExists(Long id) {
        if(!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product with id " + id + " not found.");
        }
    }


    @Override
    public Product save(ProductDto productDto) {
        Product product = productMapper.mapToEntity(productDto);
        ensureProductNameNotExists(product.getName());
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }


    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }


    @Override
    @Transactional
    public void delete(Long productId) {
        validateExists(productId);
        if (transactionDetailsRepository.existsByProduct_id(productId)) {
            throw new DataIntegrityViolationException("This product exists in a transaction. Please remove the product from the associated transaction first.");
        }
        productRepository.deleteById(productId);


    }


    @Override
    public Product findOne(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));
    }


    @Override
    public void ensureProductNameNotExists(String name) {
        if (productRepository.existsByName(name)) {
            throw new DataIntegrityViolationException("A product with this name already exists.");
        }
    }


}
