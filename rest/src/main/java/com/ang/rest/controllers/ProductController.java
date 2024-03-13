package com.ang.rest.controllers;


import com.ang.rest.domain.dto.ProductDto;
import com.ang.rest.domain.dto.TransactionDto;
import com.ang.rest.domain.entities.ProductEntity;
import com.ang.rest.domain.entities.TransactionEntity;
import com.ang.rest.mappers.impl.ProductMapper;
import com.ang.rest.services.ProductService;
import com.ang.rest.services.TransactionService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class ProductController {

    private ProductService productService;
    private TransactionService transactionService;

    private ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper, TransactionService transactionService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.transactionService = transactionService;
    }


    @PostMapping(path = "/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) throws ChangeSetPersister.NotFoundException {
        ProductEntity productEntity = productMapper.mapFrom(productDto);
        Long transactionId = productDto.getTransactionId();
        TransactionEntity transaction = transactionService.findOne(transactionId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        productEntity.setTransaction(transaction);
        ProductEntity savedProduct = productService.createProduct(productEntity);
        return new ResponseEntity<>(productMapper.mapTo(savedProduct), HttpStatus.CREATED);
    }

    @GetMapping(path = "/products")
    public List<ProductDto> getProducts() {
        List<ProductEntity> products = productService.findAll();
        return products.stream().map(productEntity -> productMapper.mapTo(productEntity))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "products/{transactionId}")
    public List<ProductDto> findProductsByTransactionId(@PathVariable("transactionId")Long id){
        List<ProductEntity> products = productService.findByTransactionId(id);
        return products.stream().map(productEntity -> productMapper.mapTo(productEntity))
                .collect(Collectors.toList());

    }

//    @GetMapping(path = "products/{id}")
//    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long id) {
//        Optional<ProductEntity> foundProduct = productService.findOne(id);
//        return foundProduct.map(productEntity -> {
//            ProductDto productDto = productMapper.mapTo(productEntity);
//            return new ResponseEntity<>(productDto, HttpStatus.OK);
//        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    @PutMapping(path = "/products/{id}")
    public ResponseEntity<ProductDto> fullUpdateTransaction(
            @PathVariable("id") Long id,
            @RequestBody ProductDto productDto) {
        if (!productService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productDto.setId(id);
        ProductEntity productEntity = productMapper.mapFrom(productDto);
        ProductEntity savedProductEntity = productService.createProduct(productEntity);
        return new ResponseEntity<>(
                productMapper.mapTo(savedProductEntity),
                HttpStatus.OK
        );

    }
}
