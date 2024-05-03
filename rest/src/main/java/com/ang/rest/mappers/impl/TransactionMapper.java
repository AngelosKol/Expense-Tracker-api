package com.ang.rest.mappers.impl;

import com.ang.rest.domain.dto.TransactionGetDto;
import com.ang.rest.domain.dto.TransactionPostDto;
import com.ang.rest.domain.entities.Transaction;
import com.ang.rest.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper implements Mapper<Transaction, TransactionPostDto> {

    private ModelMapper modelMapper;

    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TransactionPostDto mapTo(Transaction transaction) {
       return  modelMapper.map(transaction, TransactionPostDto.class);

    }

    @Override
    public Transaction mapFrom(TransactionPostDto transactionPostDto) {
        return modelMapper.map(transactionPostDto, Transaction.class);
    }


}
