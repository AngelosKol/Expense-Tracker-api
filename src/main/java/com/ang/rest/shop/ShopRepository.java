package com.ang.rest.shop;

import com.ang.rest.domain.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long>, PagingAndSortingRepository<Shop, Long> {


    Optional<Shop> findByName(String name);


    boolean existsByName(String name);

}
