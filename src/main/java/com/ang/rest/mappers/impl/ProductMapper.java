package com.ang.rest.mappers.impl;

import com.ang.rest.domain.dto.ProductDto;
import com.ang.rest.domain.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {


    public ProductDto mapToDto(Product product) {
        return ProductDto.builder().name(product.getName()).id(product.getId()).build();
    }

    public Product mapToEntity(ProductDto productDto) {
        return Product.builder().name(productDto.getName()).id(productDto.getId()).build();
    }
}
