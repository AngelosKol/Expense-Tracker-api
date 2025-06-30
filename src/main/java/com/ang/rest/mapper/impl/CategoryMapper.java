package com.ang.rest.mapper.impl;


import com.ang.rest.domain.dto.CategoryDTO;
import com.ang.rest.domain.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO mapToDto(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getFamily()
        );
    }

    public Category mapToEntity(CategoryDTO dto) {
        return new Category(
                dto.id(),
                dto.name(),
                dto.productFamily()
        );
    }
}
