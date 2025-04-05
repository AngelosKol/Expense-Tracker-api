package com.ang.rest.mapper.impl;

import com.ang.rest.domain.dto.TransactionDTO;
import com.ang.rest.domain.entity.Shop;
import com.ang.rest.domain.entity.Transaction;
import com.ang.rest.domain.entity.User;
import com.ang.rest.shop.ShopServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransactionMapper  {

private final ShopServiceImpl shopService;

    public TransactionDTO mapToDto(Transaction transaction) {
        return  new TransactionDTO(
                transaction.getId(),
                transaction.getShop().getName(),
                transaction.getDate()
        );
        }

    public Transaction mapToEntity(TransactionDTO transactionDto, User authenticatedUser, Shop shop)  {
        return Transaction.builder()
                .id(transactionDto.id())
                .shop(shop)
                .date(transactionDto.date())
                .user(authenticatedUser)
                .build();
    }
}
