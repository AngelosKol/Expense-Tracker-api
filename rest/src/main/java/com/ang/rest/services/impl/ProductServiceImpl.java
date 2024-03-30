package com.ang.rest.services.impl;

import com.ang.rest.domain.entities.Product;
import com.ang.rest.repositories.ProductRepository;
import com.ang.rest.services.ProductService;
import jakarta.transaction.Transactional;
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
    @Transactional
    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }


    @Override
    public Optional<Product> findOne(Long id){
        return productRepository.findById(id);
    }


}
