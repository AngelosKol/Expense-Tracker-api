package com.ang.rest.mapper.impl;

import com.ang.rest.domain.dto.CategoryDTO;
import com.ang.rest.domain.entity.Category;
import com.ang.rest.domain.entity.ProductFamily;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryMapper {

    public CategoryDTO mapToDto(Category category) {
        return new CategoryDTO(
                category.id,
                category.name,
                category.family.name()
        );
    }
    public Category mapToEntity(CategoryDTO dto) {
        Category category = new Category();
        category.id = dto.id();
        category.name = dto.name();
        category.family = ProductFamily.valueOf(dto.familyName());
        return category;
    }
}
