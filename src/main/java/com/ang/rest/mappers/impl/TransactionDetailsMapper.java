package com.ang.rest.mappers.impl;

import com.ang.rest.domain.dto.TransactionDetailsDto;
import com.ang.rest.domain.entity.TransactionDetails;

import org.springframework.stereotype.Component;

@Component
public class TransactionDetailsMapper  {

    public TransactionDetailsDto mapToDto(TransactionDetails transactionDetails) {
        return TransactionDetailsDto.builder()
                .name(transactionDetails.getProduct().getName())
                .price(transactionDetails.getPrice())
                .quantity(transactionDetails.getQuantity())
                .build();

    }




}
