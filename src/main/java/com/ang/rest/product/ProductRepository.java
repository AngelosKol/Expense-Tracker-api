package com.ang.rest.product;

import com.ang.rest.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
    @NonNull
    @EntityGraph(attributePaths = {"category"})
    Page<Product> findAll(@NonNull Pageable pageable);

    @EntityGraph(attributePaths = {"category"})
    <T> Page<T> findByNameContainingIgnoreCase(String name, Pageable pageable, Class<T> type);
    Product findByName(String name);

    boolean existsByName(String name);

}
