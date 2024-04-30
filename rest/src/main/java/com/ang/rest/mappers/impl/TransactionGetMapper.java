package com.ang.rest.mappers.impl;

import com.ang.rest.domain.dto.TransactionGetDto;
import com.ang.rest.domain.entities.Transaction;
import com.ang.rest.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionGetMapper implements Mapper<Transaction, TransactionGetDto> {

    private ModelMapper modelMapper;

    public TransactionGetMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public TransactionGetDto mapTo(Transaction transaction) {
        return  modelMapper.map(transaction, TransactionGetDto.class);

    }

    public Transaction mapFrom(TransactionGetDto transactionGetDto) {
        return modelMapper.map(transactionGetDto, Transaction.class);
    }
}
