package com.ang.rest.mappers.impl;

import com.ang.rest.domain.dto.TransactionDto;
import com.ang.rest.domain.entities.Transaction;
import com.ang.rest.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper implements Mapper<Transaction, TransactionDto> {

    private ModelMapper modelMapper;

    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public TransactionDto mapTo(Transaction transaction) {
        return  modelMapper.map(transaction, TransactionDto.class);

    }

    public Transaction mapFrom(TransactionDto transactionDto) {
        return modelMapper.map(transactionDto, Transaction.class);
    }
}
