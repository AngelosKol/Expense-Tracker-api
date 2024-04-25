package com.ang.rest.controllers;

import com.ang.rest.domain.dto.ProductDto;
import com.ang.rest.domain.dto.ProductRequest;
import com.ang.rest.domain.dto.TransactionDetailsDto;
import com.ang.rest.domain.dto.TransactionDto;
import com.ang.rest.domain.entities.Product;
import com.ang.rest.domain.entities.Transaction;
import com.ang.rest.domain.entities.TransactionDetails;
import com.ang.rest.mappers.Mapper;
import com.ang.rest.mappers.impl.ProductMapper;
import com.ang.rest.mappers.impl.TransactionDetailsMapper;
import com.ang.rest.services.ProductService;
import com.ang.rest.services.TransactionDetailsService;
import com.ang.rest.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
public class TransactionController {

    private TransactionService transactionService;

    private ProductService productService;

    private TransactionDetailsService transactionDetailsService;

    private Mapper<Transaction, TransactionDto> mapper;

    private ProductMapper productMapper;

    private TransactionDetailsMapper transactionDetailsMapper;

    public TransactionController(TransactionService transactionService, ProductService productService, TransactionDetailsService transactionDetailsService,
                                 Mapper<Transaction, TransactionDto> transactionMapper,
                                 ProductMapper productMapper, TransactionDetailsMapper transactionDetailsMapper) {
        this.transactionService = transactionService;
        this.productService = productService;
        this.transactionDetailsService = transactionDetailsService;
        this.mapper = transactionMapper;
        this.productMapper = productMapper;
        this.transactionDetailsMapper = transactionDetailsMapper;
    }


    @PostMapping(path = "/transactions")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto){
        Transaction transaction = mapper.mapFrom(transactionDto);
        Transaction savedTransaction = transactionService.save(transaction);
        return new ResponseEntity<>(mapper.mapTo(savedTransaction), HttpStatus.CREATED);
    }



    @GetMapping(path = "/transactions")
    public List<TransactionDto> getTransactions(){
        List<Transaction> transactions = transactionService.findAll();
        return transactions.stream()
                .map(transaction ->{
                  TransactionDto  transactionDto = mapper.mapTo(transaction);
                  transactionDto.setShopName(transaction.getShop().getName());
                  return transactionDto;
                        })
                .collect(Collectors.toList());

    }

    @GetMapping(path = "/transactions/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable("id")Long id){
        Optional<Transaction> foundTransaction = transactionService.findOne(id);
        return foundTransaction.map(t -> {
            TransactionDto transactionDto = mapper.mapTo(t);
            return new ResponseEntity<>(transactionDto,HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/transactions/{id}/details")
    public List<TransactionDetailsDto> getTransactionDetailsByTransactionId(
            @PathVariable Long id) {
        List<TransactionDetails> transactionDetails =
                transactionDetailsService.getTransactionDetailsByTransactionId(id);


        return transactionDetails.stream().map(transactionDetail ->
                transactionDetailsMapper.mapTo(transactionDetail))
                .collect(Collectors.toList());
    }


    @PutMapping(path = "/transactions/{id}")
    public ResponseEntity<TransactionDto> fullUpdateTransaction(
            @PathVariable("id")Long id,
            @RequestBody TransactionDto transactionDto){
      if(!transactionService.isExists(id)){
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      transactionDto.setId(id);
      Transaction transaction = mapper.mapFrom(transactionDto);
      Transaction savedTransaction =  transactionService.save(transaction);

      return  new ResponseEntity<>(
              mapper.mapTo(savedTransaction),
              HttpStatus.OK
      );
    }

    @DeleteMapping(path = "/transactions/{id}")
    public ResponseEntity deleteTransaction(@PathVariable("id")Long id){
        if(!transactionService.isExists(id)){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        transactionService.delete(id);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
    }





}
