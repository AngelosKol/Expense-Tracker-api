package com.ang.rest.mappers.impl;

import com.ang.rest.domain.dto.TransactionDetailsDto;
import com.ang.rest.domain.entities.TransactionDetails;
import com.ang.rest.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionDetailsMapper implements Mapper<TransactionDetails, TransactionDetailsDto> {

    private ModelMapper modelMapper;

    public TransactionDetailsMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TransactionDetailsDto mapTo(TransactionDetails transactionDetails) {
     return   modelMapper.map(transactionDetails, TransactionDetailsDto.class);

    }

    @Override
    public TransactionDetails mapFrom(TransactionDetailsDto transactionDetailsDto) {
        return modelMapper.map(transactionDetailsDto, TransactionDetails.class);
    }
}
