package com.ang.rest.product;

import com.ang.rest.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {


    Product save(Product product);

    List<Product> findAll();


    Page<Product> findAll(Pageable pageable);


    Product findOne(Long id);

    boolean isExists(Long id);


    void delete(Long productId);

    void ensureProductNameNotExists(String name);
}
