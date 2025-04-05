package com.ang.rest.shop;

import com.ang.rest.domain.dto.ShopDTO;
import com.ang.rest.domain.entity.Shop;
import com.ang.rest.mapper.impl.ShopMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/shops")
@RequiredArgsConstructor
public class ShopControllerImpl {

    private final ShopService shopService;
    private final ShopMapper shopMapper;



    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Shop> getShop(@PathVariable Long id) {
        Shop shop = shopService.findOne(id);
        return ResponseEntity.ok(shop);
    }


    @GetMapping
    public Page<ShopDTO> getShops(Pageable pageable) {
        Page<Shop> shops = shopService.findAll(pageable);
        return shops.map(shop -> shopMapper.mapToDto(shop));
    }

    @GetMapping(path = "/all")
    public List<ShopDTO> getShops() {
        List<Shop> shops = shopService.findAll();
        return shops.stream().map(shop -> shopMapper.mapToDto(shop)).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ShopDTO> createShop(@RequestBody ShopDTO shopDto) {
        Shop shop = shopService.save(shopDto);
        ShopDTO savedShopDTO = shopMapper.mapToDto(shop);
        return new ResponseEntity<>(savedShopDTO, HttpStatus.CREATED);
    }

    @PutMapping(path = "/id/{id}")
    public ResponseEntity<ShopDTO> updateShop(@PathVariable Long id, @RequestBody ShopDTO shopDto) {
        shopService.validateExists(id);
        ShopDTO shopDTO = shopDto.withId(id);
        Shop savedShop = shopService.save(shopDTO);
        return new ResponseEntity<>(shopMapper.mapToDto(savedShop), HttpStatus.OK);
    }

    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<Void> deleteShop(@PathVariable Long id) {
        shopService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
