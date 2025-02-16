package com.ang.rest.mapper.impl;

import com.ang.rest.domain.dto.ShopDto;
import com.ang.rest.domain.entity.Shop;
import org.springframework.stereotype.Component;

@Component
public class ShopMapper  {


    public ShopDto mapToDto(Shop shop) {
        return ShopDto.builder().name(shop.getName()).id(shop.getId()).build();
    }

    public Shop mapToEntity(ShopDto shopDto) {
        return Shop.builder().name(shopDto.getName()).id(shopDto.getId()).build();
    }
}
