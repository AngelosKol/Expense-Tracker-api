package com.ang.rest.mapper.impl;

import com.ang.rest.domain.dto.CategoryDTO;
import com.ang.rest.domain.entity.Category;
import com.ang.rest.domain.entity.ProductFamily;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryMapper {

    public CategoryDTO mapToDto(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getFamily().name()
        );
    }
    public Category mapToEntity(CategoryDTO dto) {
        return Category.builder().id(dto.id()).name(dto.name()).family(ProductFamily.valueOf(dto.familyName())).build();
    }
}
