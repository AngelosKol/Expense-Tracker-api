package com.ang.rest.mapper.impl;

import com.ang.rest.domain.dto.ProductDTO;
import com.ang.rest.domain.entity.Category;
import com.ang.rest.domain.entity.Product;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductMapper {


    public ProductDTO mapToDto(Product product) {
        return new ProductDTO(
                product.id,
                product.name,
                product.category != null ? product.category.name: null,
                product.category != null ? product.category.id : null
        );
    }

    public Product mapToEntity(ProductDTO productDto, Category category) {
        Product product = new Product();
        product.id = productDto.id();
        product.name = productDto.name();
        product.category = category;
        return product;

    }
    public void updateEntityFromDto(Product product, ProductDTO dto, Category category) {
        product.name = dto.name();
        product.category = category;
    }
}
