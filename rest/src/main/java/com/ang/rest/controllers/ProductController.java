package com.ang.rest.controllers;

import com.ang.rest.Exceptions.ErrorResponse;
import com.ang.rest.domain.dto.ProductDto;
import com.ang.rest.domain.entities.Product;
import com.ang.rest.mappers.impl.ProductMapper;
import com.ang.rest.services.ProductService;
import com.ang.rest.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDto.class)) })
    })
    @PostMapping(path = "/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product product = productMapper.mapFrom(productDto);
        Product savedProduct = productService.save(product);
        return new ResponseEntity<>(productMapper.mapTo(savedProduct), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of products",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDto.class)) })
    })
    @GetMapping(path = "/products/all")
    public List<ProductDto> getAllProducts() {
        List<Product> products = productService.findAll();
        return products.stream().map(product -> productMapper.mapTo(product)).collect(Collectors.toList());
    }

    @Operation(summary = "Get products with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paginated list of products",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)) })
    })
    @GetMapping(path = "/products")
    public Page<ProductDto> getProducts(Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        return products.map(productEntity -> productMapper.mapTo(productEntity));
    }

    @Operation(summary = "Get a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(path = "/products/id/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        Product product = productService.findOne(id);
        ProductDto productDto = productMapper.mapTo(product);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @Operation(summary = "Update a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping(path = "/products/id/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable("id") Long id,
            @RequestBody ProductDto productDto) {
        Product existingProduct = productService.findOne(id);

        productDto.setId(id);
        Product product = productMapper.mapFrom(productDto);
        Product savedProduct = productService.save(product);
        return new ResponseEntity<>(
                productMapper.mapTo(savedProduct),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping(path = "/products/id/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
