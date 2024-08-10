package com.ang.rest.controllers;

import com.ang.rest.exceptions.ErrorResponse;
import com.ang.rest.domain.dto.ShopDto;
import com.ang.rest.domain.entities.Shop;
import com.ang.rest.mappers.impl.ShopMapper;
import com.ang.rest.services.ShopService;
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
@RequestMapping("/shops")
public class ShopController {

    private final ShopService shopService;
    private final ShopMapper shopMapper;

    public ShopController(ShopService shopService, ShopMapper shopMapper) {
        this.shopService = shopService;
        this.shopMapper = shopMapper;
    }

    @Operation(summary = "Get a shop by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the shop", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Shop.class))}), @ApiResponse(responseCode = "404", description = "Shop not found", content = @Content(mediaType = "application/json"))})
    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Shop> getShop(@PathVariable Long id) {
        Shop shop = shopService.findOne(id);
        return ResponseEntity.ok(shop);
    }

    @Operation(summary = "Get all shops with pagination")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the shops", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))}),

    })
    @GetMapping
    public Page<ShopDto> getShops(Pageable pageable) {
        Page<Shop> shops = shopService.findAll(pageable);
        return shops.map(shop -> shopMapper.mapTo(shop));
    }

    @Operation(summary = "Get all shops")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the shops", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})})

    @GetMapping(path = "/all")
    public List<ShopDto> getShops() {
        List<Shop> shops = shopService.findAll();
        return shops.stream().map(shop -> shopMapper.mapTo(shop)).collect(Collectors.toList());
    }

    @Operation(summary = "Create a new shop")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Shop created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ShopDto.class))})})

    @PostMapping
    public ResponseEntity<ShopDto> createShop(@RequestBody ShopDto shopDto) {
        Shop shop = shopMapper.mapFrom(shopDto);
        Shop savedShop = shopService.save(shop);
        return new ResponseEntity<>(shopMapper.mapTo(savedShop), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a shop by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Shop updated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ShopDto.class))}), @ApiResponse(responseCode = "404", description = "Shop not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))), @ApiResponse(responseCode = "409", description = "Shop already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping(path = "/id/{id}")
    public ResponseEntity<ShopDto> updateShop(@PathVariable Long id, @RequestBody ShopDto shopDto) {
        Shop existingShop = shopService.findOne(id);
        shopDto.setId(id);
        Shop shop = shopMapper.mapFrom(shopDto);
        Shop savedShop = shopService.save(shop);
        return new ResponseEntity<>(shopMapper.mapTo(savedShop), HttpStatus.OK);
    }

    @Operation(summary = "Delete a shop by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Shop deleted", content = @Content), @ApiResponse(responseCode = "404", description = "Shop not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<Void> deleteShop(@PathVariable Long id) {
        shopService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
