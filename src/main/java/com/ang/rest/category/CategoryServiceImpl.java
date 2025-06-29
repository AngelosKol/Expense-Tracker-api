package com.ang.rest.category;

import com.ang.rest.domain.dto.CategoryDTO;
import com.ang.rest.domain.entity.Category;
import com.ang.rest.exception.ResourceNotFoundException;
import com.ang.rest.mapper.impl.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl {

    private  final CategoryRepository repository;
    private final CategoryMapper categoryMapper;

    public CategoryDTO  findById(Long id) {
        Category category = repository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Category with id " + id  + " not found"));
        return categoryMapper.mapToDto(category);
    }
    public CategoryDTO findByName(String  name) {
        Category category = repository.findByName(name).orElseThrow( () -> new ResourceNotFoundException("Category with name " + name  + " not found"));
        return categoryMapper.mapToDto(category);

    }
    public Category findByNameEntity(String  name) {
       return repository.findByName(name).orElseThrow( () -> new ResourceNotFoundException("Category with name " + name  + " not found"));
    }

    public List<CategoryDTO> findAll() {
        return this.repository.findAllByOrderByNameAsc().stream().map(
                c -> new CategoryDTO(c.getId(),c.getName(), c.getFamily())
        ).collect(Collectors.toList());
    }
}
