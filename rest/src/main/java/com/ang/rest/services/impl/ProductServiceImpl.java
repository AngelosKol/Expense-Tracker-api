package com.ang.rest.services.impl;

import com.ang.rest.domain.entities.ProductEntity;
import com.ang.rest.repositories.ProductRepository;
import com.ang.rest.services.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean isExists(Long id) {
        return productRepository.existsById(id);
    }

    @Override
    public ProductEntity createProduct( ProductEntity product) {
        return productRepository.save(product);
    }

    @Override
    public List<ProductEntity> findAll() {
        return StreamSupport.stream(productRepository.
                findAll()
                .spliterator(),false)
                .collect(Collectors.toList());

    }



    @Override
    public Optional<ProductEntity> findOne(Long id){
        return productRepository.findById(id);
    }

    @Override
    public List<ProductEntity> findByTransactionId(Long id){
        return productRepository.findByTransactionId(id);
    }

    @Override
    public Page<ProductEntity> findByTransactionId(Long id, Pageable pageable) {
        return productRepository.findByTransactionId(id, pageable);
    }


    @Override
    @Transactional
    public void deleteProduct(Long tid, Long pid){
        productRepository.deleteProduct(tid, pid);
    }


}
