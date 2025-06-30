package com.ang.rest.mapper.impl;

import com.ang.rest.domain.dto.ProductDTO;
import com.ang.rest.domain.entity.Category;
import com.ang.rest.domain.entity.MeasuringType;
import com.ang.rest.domain.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {


    public ProductDTO mapToDto(Product product) {
        if (product == null) return null;

        return new ProductDTO(
                product.getId(),
                product.getName(),
                getCategoryName(product),
                getCategoryId(product),
                getMeasuringType(product)
        );
    }

    public Product mapToEntity(ProductDTO productDto, Category category) {
        if(productDto == null) return null;
        return Product.builder()
                .id(productDto.id())
                .name(productDto.name())
                .category(category)
                .measuringType(MeasuringType.of(productDto.measuringType().name()))
                .build();
    }

    public String getCategoryName(Product product) {
        return product.getCategory() != null ? product.getCategory().getName() : null;
    }
    private Long getCategoryId(Product product) {
        return product.getCategory() != null ? product.getCategory().getId() : null;
    }
    public MeasuringType getMeasuringType(Product product) {
        return product.getMeasuringType() != null ? product.getMeasuringType() : MeasuringType.UNKNOWN;
    }
}
