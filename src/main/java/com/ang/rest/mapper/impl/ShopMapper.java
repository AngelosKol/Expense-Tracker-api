package com.ang.rest.mapper.impl;

import com.ang.rest.domain.dto.ShopDTO;
import com.ang.rest.domain.entity.Shop;
import org.springframework.stereotype.Component;

@Component
public class ShopMapper {
    public ShopDTO mapToDto(Shop shop) {
        return new ShopDTO(
                shop.getId(),
                shop.getName()
        );
    }

    public Shop mapToEntity(ShopDTO shopDto) {
        return Shop.builder().name(shopDto.name()).id(shopDto.id()).build();
    }
}
