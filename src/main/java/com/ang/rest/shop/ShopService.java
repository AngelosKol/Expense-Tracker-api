package com.ang.rest.shop;

import com.ang.rest.domain.dto.ShopDTO;
import com.ang.rest.domain.entity.Shop;
import io.quarkus.panache.common.Page;
import java.util.List;

public interface ShopService {



    Shop save(ShopDTO shopDto);
    ShopDTO update(Long id, ShopDTO dto);

    List<ShopDTO> findAll();

    List<ShopDTO> findAll(String filter, Page page);

    ShopDTO findOne(Long id);

    Shop findOneEntity(Long id);

    Shop findByName(String name);

    void delete(Long id) ;
}
