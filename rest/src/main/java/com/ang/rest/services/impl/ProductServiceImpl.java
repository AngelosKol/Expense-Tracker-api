package com.ang.rest.services.impl;

import com.ang.rest.Exceptions.ResourceNotFoundException;
import com.ang.rest.domain.entities.Product;
import com.ang.rest.repositories.ProductRepository;
import com.ang.rest.repositories.TransactionDetailsRepository;
import com.ang.rest.services.ProductService;
import com.ang.rest.services.TransactionDetailsService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private TransactionDetailsService transactionDetailsService;

    public ProductServiceImpl(ProductRepository productRepository, TransactionDetailsService transactionDetailsService) {
        this.productRepository = productRepository;
        this.transactionDetailsService = transactionDetailsService;
    }

    @Override
    public boolean isExists(Long id) {
        return productRepository.existsById(id);
    }


    @Override
    public boolean existsByName(String name){
        return productRepository.findByCustomCriteria(name);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return StreamSupport.stream(productRepository.
                findAll()
                .spliterator(),false)
                .collect(Collectors.toList());
    }



    @Override
    public Page<Product> findAll(Pageable pageable) {
       return productRepository.findAll(pageable);
    }


    @Override
    @Transactional
    public void delete(Long productId) throws DataIntegrityViolationException {
        Product product = findOne(productId);
        transactionDetailsService.ensureProductNotInTransaction(productId);
        productRepository.deleteById(productId);


    }


    @Override
    public Product findOne(Long id){
        return productRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Product with id " + id + " not found."));
    }


}
