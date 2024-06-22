package com.ang.rest.controllers;

import com.ang.rest.domain.dto.ShopDto;
import com.ang.rest.domain.entities.Shop;
import com.ang.rest.mappers.impl.ShopMapper;
import com.ang.rest.services.ShopService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ShopController {

    private ShopService shopService;

    private ShopMapper shopMapper;

    public ShopController(ShopService shopService, ShopMapper shopMapper){
        this.shopService = shopService;
        this.shopMapper = shopMapper;
    }


    @GetMapping(path = "/shops/{id}")
    public ResponseEntity<Shop> getShop(@PathVariable Long id){
        Shop shop = shopService.findOne(id);
        return ResponseEntity.ok(shop);
    }


    @GetMapping(path = "/shops")
    public Page<ShopDto> getShops(Pageable pageable){
        Page<Shop> shops = shopService.findAll(pageable);
        return shops.map(shop -> shopMapper.mapTo(shop));
    }

    @GetMapping(path = "/shops/all")
    public List<ShopDto> getShops(){
        List<Shop> shops = shopService.findAll();
        return shops.stream().map(shop -> shopMapper.mapTo(shop))
                .collect(Collectors.toList());
    }


    @PostMapping(path = "/shops")
    public ResponseEntity<ShopDto> createShop(@RequestBody ShopDto shopDto){
        Shop shop = shopMapper.mapFrom(shopDto);
        Shop savedShop = shopService.save(shop);
        return new ResponseEntity<>(shopMapper.mapTo(savedShop), HttpStatus.CREATED);
    }

    @PutMapping(path = "/shops/{id}")
    public ResponseEntity<ShopDto> updateShop(@PathVariable Long id ,@RequestBody ShopDto shopDto){
        Shop existingShop = shopService.findOne(id);
        shopDto.setId(id);
        Shop shop = shopMapper.mapFrom(shopDto);
        Shop savedShop = shopService.save(shop);
        return new ResponseEntity<>(shopMapper.mapTo(savedShop), HttpStatus.OK);

    }


    @DeleteMapping(path = "/shops/{id}")
    public ResponseEntity<Void> deleteShop(@PathVariable Long id){

        shopService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

