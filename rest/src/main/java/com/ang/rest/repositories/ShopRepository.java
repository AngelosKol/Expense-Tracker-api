package com.ang.rest.repositories;

import com.ang.rest.domain.entities.Product;
import com.ang.rest.domain.entities.Shop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ShopRepository extends CrudRepository<Shop, Long>,
        PagingAndSortingRepository<Shop, Long> {


   Optional<Shop>  findByName(String name);


   boolean existsByName(String name);

}
