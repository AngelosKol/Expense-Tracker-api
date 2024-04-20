package com.ang.rest.repositories;

import com.ang.rest.domain.entities.Shop;
import org.springframework.data.repository.CrudRepository;

public interface ShopRepository extends CrudRepository<Shop, Long> {
}
