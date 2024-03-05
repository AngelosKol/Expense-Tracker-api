package com.ang.rest.services.impl;

import com.ang.rest.domain.entities.ProductEntity;
import com.ang.rest.repositories.ProductRepository;
import com.ang.rest.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity createProduct( ProductEntity product) {
        return productRepository.save(product);
    }

    @Override
    public List<ProductEntity> findAll() {
        return null;
    }
}
