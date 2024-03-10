package com.ang.rest.controllers;


import com.ang.rest.domain.dto.ProductDto;
import com.ang.rest.domain.entities.ProductEntity;
import com.ang.rest.mappers.impl.ProductMapper;
import com.ang.rest.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class ProductController {

private ProductService productService;

private ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }



    @PostMapping(path = "/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        ProductEntity productEntity = productMapper.mapFrom(productDto);
        ProductEntity savedProduct = productService.createProduct(productEntity);
        return new ResponseEntity<>(productMapper.mapTo(savedProduct), HttpStatus.CREATED);
    }

    @GetMapping(path = "/products")
    public List<ProductDto> getProducts(){
        List<ProductEntity> products = productService.findAll();
        return products.stream().map(productEntity -> productMapper.mapTo(productEntity))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id")Long id){
        Optional<ProductEntity> foundProduct = productService.findOne(id);
        return foundProduct.map(productEntity -> {
            ProductDto productDto = productMapper.mapTo(productEntity);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
