package com.ang.rest.controllers;


import com.ang.rest.domain.dto.ProductDto;
import com.ang.rest.domain.entities.ProductEntity;
import com.ang.rest.mappers.impl.ProductMapper;
import com.ang.rest.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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

}
