package com.ang.rest.controllers;

import com.ang.rest.domain.dto.TransactionDto;
import com.ang.rest.domain.entities.TransactionEntity;
import com.ang.rest.mappers.Mapper;
import com.ang.rest.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    private TransactionService transactionService;

    private Mapper<TransactionEntity, TransactionDto> authorMapper;

    public TransactionController(TransactionService transactionService, Mapper<TransactionEntity, TransactionDto> authorMapper) {
        this.transactionService = transactionService;
        this.authorMapper = authorMapper;
    }


    @PostMapping(path = "/transactions")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto){
        TransactionEntity transactionEntity = authorMapper.mapFrom(transactionDto);
        TransactionEntity savedTransaction = transactionService.save(transactionEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedTransaction), HttpStatus.CREATED);
    }
}
