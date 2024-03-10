package com.ang.rest.services;

import com.ang.rest.domain.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductEntity createProduct( ProductEntity product);

    List<ProductEntity> findAll();

    Optional<ProductEntity> findOne(Long id);
}
