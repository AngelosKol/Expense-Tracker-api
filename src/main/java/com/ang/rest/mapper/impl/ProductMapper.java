package com.ang.rest.mapper.impl;

import com.ang.rest.domain.dto.ProductDTO;
import com.ang.rest.domain.entity.Category;
import com.ang.rest.domain.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {


    public ProductDTO mapToDto(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory() != null ? product.getCategory().getName() : null,
                product.getCategory() != null ? product.getCategory().getId() : null
        );
    }

    public Product mapToEntity(ProductDTO productDto, Category category) {
        return Product.builder()
                .id(productDto.id())
                .name(productDto.name())
                .category(category)
                .build();
    }
}
