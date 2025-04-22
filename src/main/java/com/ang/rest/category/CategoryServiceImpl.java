package com.ang.rest.category;

import com.ang.rest.domain.dto.CategoryDTO;
import com.ang.rest.domain.entity.Category;
import com.ang.rest.exception.ResourceNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoryServiceImpl {

    @Inject
    CategoryRepository repository;

    public Category findOne(Long id) {
        Category category = repository.findById(id);
        if(category == null) throw new ResourceNotFoundException("Category with id " + id  + " not found");
        return category;
    }
    public Category findByName(String  name) {
        return repository.findByColumnNameIgnoreCase(name).firstResultOptional()
                .orElseThrow( () -> new ResourceNotFoundException("Category with name " + name  + " not found"));
    }

    public Category findCategory(String name) {
        return repository.findByNameLight(name);
    }
    public List<CategoryDTO> findAll() {
        return repository.findAllByOrderByColumnAsc("name").stream().map(
                category -> new CategoryDTO(category.id,category.name, category.family.name())
        ).collect(Collectors.toList());
    }
}
