package com.ang.rest.category;

import com.ang.rest.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, PagingAndSortingRepository<Category, Long> {
    Optional<Category> findByName(String name);
    List<Category> findAllByOrderByNameAsc();

    @Query("SELECT new com.ang.rest.domain.entity.Category(id, name, family) FROM Category c WHERE c.name = :name")
    Category findCategoryWithOutProducts(@Param("name") String name);
}
