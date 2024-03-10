package com.ang.rest.controllers;

import com.ang.rest.domain.dto.TransactionDto;
import com.ang.rest.domain.entities.TransactionEntity;
import com.ang.rest.mappers.Mapper;
import com.ang.rest.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TransactionController {

    private TransactionService transactionService;

    private Mapper<TransactionEntity, TransactionDto> mapper;

    public TransactionController(TransactionService transactionService, Mapper<TransactionEntity, TransactionDto> authorMapper) {
        this.transactionService = transactionService;
        this.mapper = authorMapper;
    }


    @PostMapping(path = "/transactions")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto){
        TransactionEntity transactionEntity = mapper.mapFrom(transactionDto);
        TransactionEntity savedTransaction = transactionService.save(transactionEntity);
        return new ResponseEntity<>(mapper.mapTo(savedTransaction), HttpStatus.CREATED);
    }

    @GetMapping(path = "/transactions")
    public List<TransactionDto> getTransactions(){
       List<TransactionEntity> transactions = transactionService.findAll();
       return transactions.stream().map(transactionEntity -> mapper.mapTo(transactionEntity))
               .collect(Collectors.toList());
    }

    @GetMapping(path = "/transactions/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable("id")Long id){
      Optional<TransactionEntity> foundTransaction =  transactionService.findOne(id);
      return foundTransaction.map(transactionEntity -> {
            TransactionDto transactionDto = mapper.mapTo(transactionEntity);
            return new ResponseEntity<>(transactionDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
