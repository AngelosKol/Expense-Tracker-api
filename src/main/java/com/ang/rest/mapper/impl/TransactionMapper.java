package com.ang.rest.mapper.impl;

import com.ang.rest.domain.dto.TransactionDTO;
import com.ang.rest.domain.entity.Shop;
import com.ang.rest.domain.entity.Transaction;
import com.ang.rest.domain.entity.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransactionMapper  {

    public TransactionDTO mapToDto(Transaction transaction) {
        return  new TransactionDTO(
                transaction.id,
                transaction.shop.name,
                transaction.date
        );
        }

    public Transaction mapToEntity(TransactionDTO transactionDto, User authenticatedUser, Shop shop)  {
        Transaction transaction = new Transaction();
        transaction.id = transactionDto.id();
        transaction.shop = shop;
        transaction.date = transactionDto.date();
        transaction.user = authenticatedUser;
        return transaction;
    }
}
