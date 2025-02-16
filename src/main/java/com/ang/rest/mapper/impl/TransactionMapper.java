package com.ang.rest.mapper.impl;

import com.ang.rest.domain.dto.TransactionDto;
import com.ang.rest.domain.entity.Shop;
import com.ang.rest.domain.entity.Transaction;
import com.ang.rest.shop.ShopServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransactionMapper  {

private final ShopServiceImpl shopService;

    public TransactionDto mapToDto(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .shopName(transaction.getShop().getName())
                .date(transaction.getDate())
                .build();
        }

    public Transaction mapToEntity(TransactionDto transactionDto)  {
        Shop shop = shopService.findByName(transactionDto.getShopName());
        return Transaction.builder()
                .id(transactionDto.getId())
                .shop(shop)
                .date(transactionDto.getDate())
                .build();
    }
}
