package com.ang.rest.mapper.impl;

import com.ang.rest.domain.dto.ShopDTO;
import com.ang.rest.domain.entity.Shop;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ShopMapper {
    public ShopDTO mapToDto(Shop shop) {
        return new ShopDTO(shop.id, shop.name);
    }

    public Shop mapToEntity(ShopDTO shopDto) {
        Shop shop = new Shop();
        shop.id = shopDto.id();
        shop.name = shopDto.name();
        return shop;
    }

    public void updateEntityFromDto(Shop shop, ShopDTO shopDTO) {
        shop.name = shopDTO.name();
    }
}
