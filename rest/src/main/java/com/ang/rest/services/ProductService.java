package com.ang.rest.services;

import com.ang.rest.domain.entities.ProductEntity;

import java.util.List;

public interface ProductService {

    ProductEntity createProduct( ProductEntity product);

    List<ProductEntity> findAll();
}
