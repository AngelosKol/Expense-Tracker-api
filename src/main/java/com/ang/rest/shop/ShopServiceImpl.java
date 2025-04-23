package com.ang.rest.shop;

import com.ang.rest.domain.dto.ShopDTO;
import com.ang.rest.exception.ResourceConflictException;
import com.ang.rest.exception.ResourceNotFoundException;
import com.ang.rest.domain.entity.Shop;
import com.ang.rest.mapper.impl.ShopMapper;
import com.ang.rest.transaction.TransactionRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.quarkus.panache.common.Page;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ShopServiceImpl implements ShopService {

    @Inject
    ShopRepository shopRepository;

    @Inject TransactionRepository transactionRepository;

    @Inject ShopMapper shopMapper;

    @Override
    public Shop save(ShopDTO shopDto) {
        if (shopRepository.existsByName(shopDto.name())) {
            throw new ResourceConflictException("A shop with this name already exists.");
        }
        Shop shop = shopMapper.mapToEntity(shopDto);
        shopRepository.persist(shop);
        return shop;
    }
    @Transactional
    @Override
    public ShopDTO update(Long id, ShopDTO shopDTO) {
        Shop existingShop = shopRepository.findById(id);
        if(existingShop == null) throw new ResourceNotFoundException("Shop with id " + id + " not found.");
        if(existingShop.getName().equalsIgnoreCase(shopDTO.name())) throw new ResourceConflictException("Current name is the same with the provided name.");
        if(shopRepository.existsByName(shopDTO.name())) throw new ResourceConflictException("A shop with this name already exists.");
        shopMapper.updateEntityFromDto(existingShop, shopDTO);
        return shopMapper.mapToDto(existingShop);
    }

    @Override
    public List<ShopDTO> findAll() {
        return shopRepository.findAll().stream().map(shopMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ShopDTO> findAll(String filter, Page page) {
        PanacheQuery<Shop> query = shopRepository.findByNameIgnoreCase(filter.toLowerCase());
        return query.page(page).stream().map(shopMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ShopDTO findOne(Long id) {
        return shopRepository.findByIdOptional(id).map(shopMapper::mapToDto).orElseThrow(() -> new ResourceNotFoundException("Shop with ID " + id + " not found"));
    }

    @Override
    public Shop findOneEntity(Long id) {
        return shopRepository.findByIdOptional(id).orElseThrow(() -> new ResourceNotFoundException("Shop with ID " + id + " not found"));
    }

    @Override
    public Shop findByName(String name) {
        return shopRepository.findByExactNameIgnoreCase(name).singleResultOptional().orElseThrow(() -> new ResourceNotFoundException("Shop  not found."));
    }

    @Override
    public void delete(Long id) {
        if(!shopRepository.existsById(id)) {
            throw new ResourceNotFoundException("Shop with ID " + id + " not found");
        }
        if (transactionRepository.existsByShop_id(id)) {
            throw new ResourceConflictException("There is a transaction related with this shop.");
        }
        shopRepository.deleteById(id);
    }
}
