package com.ang.rest.category;

import com.ang.rest.domain.dto.CategoryDTO;
import com.ang.rest.domain.entity.Category;
import com.ang.rest.exception.ResourceNotFoundException;
import com.ang.rest.mapper.impl.CategoryMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoryServiceImpl {

    @Inject CategoryRepository repository;
    @Inject CategoryMapper mapper;

    public Category findOneEntity(Long id) {
        return repository.findByIdOptional(id).orElseThrow(() ->  new ResourceNotFoundException("Category with id " + id  + " not found"));
    }
    public CategoryDTO findOne(Long id) {
        return repository.findByIdOptional(id).map(mapper::mapToDto).orElseThrow(() ->  new ResourceNotFoundException("Category with id " + id  + " not found"));
    }
    public Category findByName(String  name) {
        return repository.findByNameLight(name).singleResultOptional()
                .orElseThrow( () -> new ResourceNotFoundException("Category with name " + name  + " not found"));
    }
    public List<CategoryDTO> findAll() {
        return repository.findAllByOrderByColumnAsc("name").stream().map(
                category -> new CategoryDTO(category.getId(),category.getName(), category.getFamily().name())
        ).collect(Collectors.toList());
    }
}
