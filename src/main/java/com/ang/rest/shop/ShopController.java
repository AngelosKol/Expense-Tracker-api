package com.ang.rest.shop;

import com.ang.rest.domain.dto.ShopDTO;
import com.ang.rest.domain.entity.Shop;
import com.ang.rest.domain.dto.ErrorResponse;
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

public interface ShopController {

    @Operation(summary = "Get a shop by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the shop", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Shop.class))}), @ApiResponse(responseCode = "404", description = "Shop not found", content = @Content(mediaType = "application/json"))})
    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Shop> getShop(@PathVariable Long id) ;

    @Operation(summary = "Get all shops with pagination")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the shops", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))}),

    })
    @GetMapping
    public Page<ShopDTO> getShops(Pageable pageable);

    @Operation(summary = "Get all shops")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the shops", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})})

    @GetMapping(path = "/all")
    public List<ShopDTO> getShops() ;

    @Operation(summary = "Create a new shop")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Shop created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ShopDTO.class))})})

    @PostMapping
    public ResponseEntity<ShopDTO> createShop(@RequestBody ShopDTO shopDto);

    @Operation(summary = "Update a shop by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Shop updated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ShopDTO.class))}), @ApiResponse(responseCode = "404", description = "Shop not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))), @ApiResponse(responseCode = "409", description = "Shop already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping(path = "/id/{id}")
    public ResponseEntity<ShopDTO> updateShop(@PathVariable Long id, @RequestBody ShopDTO shopDto);

    @Operation(summary = "Delete a shop by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Shop deleted", content = @Content), @ApiResponse(responseCode = "404", description = "Shop not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<Void> deleteShop(@PathVariable Long id) ;
}
