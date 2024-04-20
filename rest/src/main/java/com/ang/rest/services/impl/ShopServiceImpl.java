package com.ang.rest.services.impl;

import com.ang.rest.domain.entities.Shop;
import com.ang.rest.repositories.ShopRepository;
import com.ang.rest.services.ShopService;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ShopServiceImpl implements ShopService {

    private ShopRepository shopRepository;

    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public Shop save(Shop shop) {
        return shopRepository.save(shop);
    }

    @Override
    public List<Shop> findAll() {
        return StreamSupport.stream(shopRepository.findAll()
                .spliterator(),false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Shop> findOne(Long id) {
        return shopRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return shopRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Not found"));

        shopRepository.deleteById(id);
    }
}
