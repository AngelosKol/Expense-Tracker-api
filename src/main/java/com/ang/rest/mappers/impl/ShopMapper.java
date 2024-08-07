package com.ang.rest.mappers.impl;

import com.ang.rest.domain.dto.ShopDto;
import com.ang.rest.domain.entities.Product;
import com.ang.rest.domain.entities.Shop;
import com.ang.rest.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ShopMapper implements Mapper<Shop, ShopDto> {

    private ModelMapper modelMapper;

    public ShopMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ShopDto mapTo(Shop shop) {
        return modelMapper.map(shop, ShopDto.class);
    }

    @Override
    public Shop mapFrom(ShopDto shopDto) {
        return modelMapper.map(shopDto, Shop.class);
    }
}
