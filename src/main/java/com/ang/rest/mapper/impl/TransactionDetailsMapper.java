package com.ang.rest.mapper.impl;

import com.ang.rest.domain.dto.TransactionDetailsDTO;
import com.ang.rest.domain.entity.TransactionDetails;

import org.springframework.stereotype.Component;

@Component
public class TransactionDetailsMapper  {
    public TransactionDetailsDTO mapToDto(TransactionDetails transactionDetails) {
        return new TransactionDetailsDTO(
                transactionDetails.getProduct().getName(),
                transactionDetails.getPrice(),
                transactionDetails.getQuantity()
        );
    }
}
