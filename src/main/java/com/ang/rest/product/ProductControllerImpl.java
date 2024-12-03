package com.ang.rest.product;

import com.ang.rest.domain.dto.ProductDto;
import com.ang.rest.domain.entity.Product;
import com.ang.rest.mappers.impl.ProductMapper;
import com.ang.rest.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductControllerImpl {

    private final ProductService productService;
    private final ProductMapper productMapper;


    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product savedProduct = productService.save(productDto);
        ProductDto savedProductDto = productMapper.mapToDto(savedProduct);
        return new ResponseEntity<>(savedProductDto, HttpStatus.CREATED);
    }


    @GetMapping(path = "/all")
    public List<ProductDto> getAllProducts() {
        List<Product> products = productService.findAll();
        return products.stream().map(product -> productMapper.mapToDto(product)).collect(Collectors.toList());
    }


    @GetMapping
    public Page<ProductDto> getProducts(Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        return products.map(productEntity -> productMapper.mapToDto(productEntity));
    }


    @GetMapping(path = "/id/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        Product product = productService.findOne(id);
        return new ResponseEntity<>(productMapper.mapToDto(product), HttpStatus.OK);
    }

    @PutMapping(path = "/id/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        productService.validateExists(id);
        productDto.setId(id);
        Product savedProduct = productService.save(productDto);
        return new ResponseEntity<>(productMapper.mapToDto(savedProduct), HttpStatus.OK);
    }


    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
