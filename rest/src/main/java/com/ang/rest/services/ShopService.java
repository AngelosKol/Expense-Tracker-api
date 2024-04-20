package com.ang.rest.services;

import com.ang.rest.domain.entities.Shop;

import java.util.List;
import java.util.Optional;

public interface ShopService {

    Shop save(Shop shop);


    List<Shop> findAll();

    Optional<Shop> findOne(Long id);

    boolean isExists(Long id);

    void delete(Long id);
}
