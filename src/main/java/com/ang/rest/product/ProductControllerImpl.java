package com.ang.rest.product;

import com.ang.rest.domain.dto.ProductDTO;
import com.ang.rest.domain.entity.MeasuringType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductControllerImpl {

    private final ProductService productService;


    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productDto));
    }


    @GetMapping(path = "/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }


    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getProducts(
            @RequestParam(required = false, defaultValue = "") String filter, Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(filter, pageable));

    }


    @GetMapping(path = "/id/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findOne(id));
    }

    @PutMapping(path = "/id/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDto) {
        return ResponseEntity.ok(productService.update(id, productDto));
    }


    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/measuring-types")
    public ResponseEntity<MeasuringType[]> getMeasuringTypes() {
        return ResponseEntity.ok(productService.getMeasuringTypes());
    }

}
