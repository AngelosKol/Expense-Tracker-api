package com.ang.rest.services;

import com.ang.rest.domain.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {


    Product createProduct(Product product);

    List<Product> findAll();

    // Page<ProductEntity> findAll(Pageable pageable);


    Optional<Product> findOne(Long id);

    boolean isExists(Long id);

//    List<ProductEntity> findByTransactionId(Long id);

//    Page<ProductEntity> findByTransactionId(Long id,Pageable pageable);
//    void deleteProductFromTransaction(Long tid, Long pid);

    void deleteProduct(Long productId);
}
