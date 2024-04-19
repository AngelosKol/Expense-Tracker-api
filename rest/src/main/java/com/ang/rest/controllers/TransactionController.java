package com.ang.rest.controllers;

import com.ang.rest.domain.dto.ProductDto;
import com.ang.rest.domain.dto.ProductRequest;
import com.ang.rest.domain.dto.TransactionDto;
import com.ang.rest.domain.entities.Product;
import com.ang.rest.domain.entities.Transaction;
import com.ang.rest.mappers.Mapper;
import com.ang.rest.mappers.impl.ProductMapper;
import com.ang.rest.services.ProductService;
import com.ang.rest.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private Mapper<Transaction, TransactionDto> mapper;

    private ProductMapper productMapper;


    public TransactionController(TransactionService transactionService, ProductService productService,
                                 Mapper<Transaction, TransactionDto> transactionMapper, ProductMapper productMapper) {
        this.transactionService = transactionService;
        this.productService = productService;
        this.mapper = transactionMapper;
        this.productMapper = productMapper;
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
        return transactions.stream().map(transactionEntity -> mapper.mapTo(transactionEntity)).collect(Collectors.toList());
    }

    @GetMapping(path = "/transactions/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable("id")Long id){
        Optional<Transaction> foundTransaction = transactionService.findOne(id);
        return foundTransaction.map(transactionEntity -> {
            TransactionDto transactionDto = mapper.mapTo(transactionEntity);
            return new ResponseEntity<>(transactionDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping(path = "/transactions/{id}/products")
    public ResponseEntity<?> addProductToTransaction(@PathVariable("id") Long id,
                                                     @RequestBody ProductRequest request) {

           transactionService.addProductToTransaction(id, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping(path = "/transactions/{id}/products")
    public ResponseEntity<List<ProductDto>> findProductsByTransactionId(@PathVariable("id")Long id){
        var products = transactionService.getProducts(id);
        return  ResponseEntity.ok(products.stream().map(product -> productMapper.mapTo(product))
                .collect(Collectors.toList()));
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

    @DeleteMapping(path = "/transactions/{transactionId}/products/{productId}")
    public ResponseEntity deleteProductFromTransaction(@PathVariable("transactionId") Long tid,
                                                       @PathVariable("productId") Long pid) {
        if (!transactionService.isExists(tid) || !productService.isExists(pid)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        transactionService.deleteProductFromTransaction(tid, pid);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
