package com.ang.rest.services;
import com.ang.rest.domain.entities.Product;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {


    boolean existsByName(String name);

    Product createProduct(Product product);

    List<Product> findAll();


    Page<Product> findAll(Pageable pageable);


    Product findOne(Long id);

    boolean isExists(Long id);



    void delete(Long productId) throws DataIntegrityViolationException;
}
