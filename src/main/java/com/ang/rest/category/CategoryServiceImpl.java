package com.ang.rest.category;

import com.ang.rest.domain.dto.CategoryDTO;
import com.ang.rest.domain.entity.Category;
import com.ang.rest.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl {

    private  final CategoryRepository repository;

    public Category  findById(Long id) {
        return this.repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Category with id " + id  + " not found"));
    }
    public Category findByName(String  name) {
        return this.repository.findByName(name)
                .orElseThrow( () -> new ResourceNotFoundException("Category with name " + name  + " not found"));
    }

    public Category findCategory(String name) {
        return this.repository.findCategoryWithOutProducts(name);
    }
    public List<CategoryDTO> findAll() {
        return this.repository.findAllByOrderByNameAsc().stream().map(
                c -> new CategoryDTO(c.getId(),c.getName(), c.getFamily().name())
        ).collect(Collectors.toList());
    }
}
