package com.ang.rest.shop;

import com.ang.rest.domain.dto.ShopDTO;
import com.ang.rest.domain.entity.Shop;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShopService {

    void validateExists(Long id);

    Shop save(ShopDTO shopDto);

    List<Shop> findAll();


    Page<Shop> findAll(Pageable pageable);

    Shop findOne(Long id);

    Shop findByName(String name);

    boolean isExists(Long id);

    void delete(Long id) throws DataIntegrityViolationException;

    void ensureShopNameNotExists(String name);
}
