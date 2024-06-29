package com.ang.rest.services.impl;

import com.ang.rest.Exceptions.ResourceNotFoundException;
import com.ang.rest.domain.entities.Shop;
import com.ang.rest.repositories.ShopRepository;
import com.ang.rest.services.ShopService;
import com.ang.rest.services.TransactionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ShopServiceImpl implements ShopService {

    private ShopRepository shopRepository;
    private TransactionService transactionService;
    public ShopServiceImpl(ShopRepository shopRepository, TransactionService transactionService) {
        this.shopRepository = shopRepository;
        this.transactionService = transactionService;
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
    public Page<Shop> findAll(Pageable pageable){
        return  shopRepository.findAll(pageable);
    }

    @Override
    public Shop findOne(Long id) {
        return shopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shop with ID " + id + " not found"));
    }

    @Override
    public Shop findByName(String name) {
        return shopRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Shop  not found."));


    }

    @Override
    public boolean isExists(Long id) {
        return shopRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        Shop shop = findOne(id);
        transactionService.ensureShopNotInTransaction(id);
        shopRepository.deleteById(id);
    }



}
