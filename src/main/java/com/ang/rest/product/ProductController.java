package com.ang.rest.product;

import com.ang.rest.domain.dto.ProductDto;
import com.ang.rest.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProductController {
    @Operation(summary = "Create a new product")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Product created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))})})
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto);

    @Operation(summary = "Get all products")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of products", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))})})
    @GetMapping(path = "/all")
    public List<ProductDto> getAllProducts();

    @Operation(summary = "Get products with pagination")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Paginated list of products", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))})})
    @GetMapping
    public Page<ProductDto> getProducts(Pageable pageable);

    @Operation(summary = "Get a product by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Product found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))}), @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping(path = "/id/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id);

    @Operation(summary = "Update a product by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Product updated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))}), @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping(path = "/id/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto);

    @Operation(summary = "Delete a product by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Product deleted", content = @Content), @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id);
}
