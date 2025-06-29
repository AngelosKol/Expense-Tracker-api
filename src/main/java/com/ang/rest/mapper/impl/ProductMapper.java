package com.ang.rest.mapper.impl;

import com.ang.rest.domain.dto.ProductDTO;
import com.ang.rest.domain.entity.Category;
import com.ang.rest.domain.entity.MeasuringType;
import com.ang.rest.domain.entity.Product;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductMapper {


    public ProductDTO mapToDto(Product product) {
        if (product == null) return null;

        Objects.requireNonNull(product.getMeasuringType(), "MeasuringType must not be null");
        return new ProductDTO(
                product.getId(),
                product.getName(),
                getCategoryName(product),
                getCategoryId(product),
                product.getMeasuringType()
        );
    }

    public Product mapToEntity(ProductDTO productDto, Category category) {
        if(productDto == null) return null;
        return Product.builder()
                .id(productDto.id())
                .name(productDto.name())
                .category(category)
                .measuringType(productDto.measuringType())
                .build();
    }

    public String getCategoryName(Product product) {
        return product.getCategory() != null ? product.getCategory().getName() : null;
    }
    private Long getCategoryId(Product product) {
        return product.getCategory() != null ? product.getCategory().getId() : null;
    }
}
