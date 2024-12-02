package com.ang.rest.product;

import com.ang.rest.domain.dto.ProductDto;
import com.ang.rest.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {


    Product save(ProductDto product);



    List<Product> findAll();


    Page<Product> findAll(Pageable pageable);


    Product findOne(Long id);

    void validateExists(Long id);


    void delete(Long productId);

    void ensureProductNameNotExists(String name);
}
