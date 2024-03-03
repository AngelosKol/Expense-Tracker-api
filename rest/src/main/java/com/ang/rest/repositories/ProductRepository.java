package com.ang.rest.repositories;

import com.ang.rest.domain.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity,Long> {
}
