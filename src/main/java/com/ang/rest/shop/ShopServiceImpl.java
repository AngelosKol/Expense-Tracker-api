package com.ang.rest.shop;

import com.ang.rest.exceptions.ResourceNotFoundException;
import com.ang.rest.domain.entity.Shop;
import com.ang.rest.transaction.TransactionRepository;
import com.ang.rest.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    private final TransactionRepository transactionRepository;

    @Override
    public Shop save(Shop shop) {
        ensureShopNameNotExists(shop.getName());
        return shopRepository.save(shop);
    }

    @Override
    public List<Shop> findAll() {
        return StreamSupport.stream(shopRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Page<Shop> findAll(Pageable pageable) {
        return shopRepository.findAll(pageable);
    }

    @Override
    public Shop findOne(Long id) {
        return shopRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Shop with ID " + id + " not found"));
    }

    @Override
    public Shop findByName(String name) {
        return shopRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Shop  not found."));

    }

    @Override
    public boolean isExists(Long id) {
        return shopRepository.existsById(id);
    }


    @Override
    public void delete(Long id) {
        Shop shop = findOne(id);
        if (transactionRepository.existsByShop_id(id)) {
            throw new DataIntegrityViolationException("There is a transaction related with this shop.");
        }
        shopRepository.deleteById(id);
    }

    @Override
    public void ensureShopNameNotExists(String name) {
        if (shopRepository.existsByName(name)) {
            throw new DataIntegrityViolationException("A shop with this name already exists.");
        }
    }


}
