package com.ang.rest.dao;

import com.ang.rest.domain.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

     void create(ProductEntity product);

     Optional<ProductEntity> findOne(long id);

     List<ProductEntity> find();

     void update(long id, ProductEntity product);

     void delete(long id);
}
