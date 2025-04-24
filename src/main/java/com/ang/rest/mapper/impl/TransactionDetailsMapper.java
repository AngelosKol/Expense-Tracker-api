package com.ang.rest.mapper.impl;

import com.ang.rest.domain.dto.TransactionDetailsDTO;
import com.ang.rest.domain.entity.TransactionDetails;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class TransactionDetailsMapper  {
    public TransactionDetailsDTO mapToDto(TransactionDetails transactionDetails) {
        return new TransactionDetailsDTO(
                transactionDetails.product.name,
                transactionDetails.price,
                transactionDetails.quantity
        );
    }
}
