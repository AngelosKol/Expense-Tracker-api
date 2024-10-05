package com.ang.rest.shop;

import com.ang.rest.domain.dto.ShopDto;
import com.ang.rest.domain.entities.Shop;
import com.ang.rest.mappers.impl.ShopMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/shops")
public class ShopControllerImpl {

    private final ShopService shopService;
    private final ShopMapper shopMapper;

    public ShopControllerImpl(ShopService shopService, ShopMapper shopMapper) {
        this.shopService = shopService;
        this.shopMapper = shopMapper;
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Shop> getShop(@PathVariable Long id) {
        Shop shop = shopService.findOne(id);
        return ResponseEntity.ok(shop);
    }


    @GetMapping
    public Page<ShopDto> getShops(Pageable pageable) {
        Page<Shop> shops = shopService.findAll(pageable);
        return shops.map(shop -> shopMapper.mapTo(shop));
    }

    @GetMapping(path = "/all")
    public List<ShopDto> getShops() {
        List<Shop> shops = shopService.findAll();
        return shops.stream().map(shop -> shopMapper.mapTo(shop)).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ShopDto> createShop(@RequestBody ShopDto shopDto) {
        Shop shop = shopMapper.mapFrom(shopDto);
        Shop savedShop = shopService.save(shop);
        return new ResponseEntity<>(shopMapper.mapTo(savedShop), HttpStatus.CREATED);
    }

    @PutMapping(path = "/id/{id}")
    public ResponseEntity<ShopDto> updateShop(@PathVariable Long id, @RequestBody ShopDto shopDto) {
        Shop existingShop = shopService.findOne(id);
        shopDto.setId(id);
        Shop shop = shopMapper.mapFrom(shopDto);
        Shop savedShop = shopService.save(shop);
        return new ResponseEntity<>(shopMapper.mapTo(savedShop), HttpStatus.OK);
    }

    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<Void> deleteShop(@PathVariable Long id) {
        shopService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
