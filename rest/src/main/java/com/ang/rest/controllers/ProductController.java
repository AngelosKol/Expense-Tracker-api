package com.ang.rest.controllers;


import com.ang.rest.domain.dto.ProductDto;
import com.ang.rest.domain.entities.Product;
import com.ang.rest.mappers.impl.ProductMapper;
import com.ang.rest.services.ProductService;
import com.ang.rest.services.TransactionService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        Product product = productMapper.mapFrom(productDto);
        Product savedProduct = productService.createProduct(product);
        return new ResponseEntity<>(productMapper.mapTo(savedProduct), HttpStatus.CREATED);
    }


    @GetMapping(path = "/products/all")
    public List<ProductDto> getAllProducts(){
        List<Product> products = productService.findAll();
        return products.stream().map(product -> productMapper.mapTo(product)).collect(Collectors.toList());
    }

    @GetMapping(path = "/products")
    public Page<ProductDto> getProducts(Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        return products.map(productEntity -> productMapper.mapTo(productEntity));
    }

    @PutMapping(path = "/products/{id}")
    public ResponseEntity<ProductDto> fullUpdateProduct(
            @PathVariable("id") Long id,
            @RequestBody ProductDto productDto) {
        if (!productService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productDto.setId(id);
        Product product = productMapper.mapFrom(productDto);
        Product savedProduct = productService.createProduct(product);
        return new ResponseEntity<>(
                productMapper.mapTo(savedProduct),
                HttpStatus.OK
        );
    }

    @DeleteMapping(path = "/products/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id")Long id){
        if (!productService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.deleteProduct(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
