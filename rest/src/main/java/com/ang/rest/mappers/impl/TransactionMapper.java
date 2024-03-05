package com.ang.rest.mappers.impl;

import com.ang.rest.domain.dto.TransactionDto;
import com.ang.rest.domain.entities.TransactionEntity;
import com.ang.rest.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper implements Mapper<TransactionEntity, TransactionDto> {

    private ModelMapper modelMapper;

    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TransactionDto mapTo(TransactionEntity transactionEntity) {
       return  modelMapper.map(transactionEntity, TransactionDto.class);

    }

    @Override
    public TransactionEntity mapFrom(TransactionDto transactionDto) {
        return modelMapper.map(transactionDto, TransactionEntity.class);
    }
}
