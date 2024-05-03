package com.ang.rest.services;

import com.ang.rest.domain.entities.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ShopService {

    Shop save(Shop shop);


    List<Shop> findAll();


    Page<Shop> findAll(Pageable pageable);

    Optional<Shop> findOne(Long id);

    Optional<Shop> findByName(String name);
    boolean isExists(Long id);

    void delete(Long id);
}
