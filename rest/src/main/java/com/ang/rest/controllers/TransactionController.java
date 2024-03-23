package com.ang.rest.controllers;

import com.ang.rest.domain.dto.ProductDto;
import com.ang.rest.domain.dto.TransactionDto;
import com.ang.rest.domain.entities.ProductEntity;
import com.ang.rest.domain.entities.TransactionEntity;
import com.ang.rest.mappers.Mapper;
import com.ang.rest.mappers.impl.ProductMapper;
import com.ang.rest.services.ProductService;
import com.ang.rest.services.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TransactionController {

    private TransactionService transactionService;

    private ProductService productService;

    private Mapper<TransactionEntity, TransactionDto> mapper;

    private ProductMapper productMapper;


    public TransactionController(TransactionService transactionService,ProductService productService, Mapper<TransactionEntity, TransactionDto> transactionMapper,ProductMapper productMapper) {
        this.transactionService = transactionService;
        this.productService = productService;
        this.mapper = transactionMapper;
        this.productMapper = productMapper;
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

    @PutMapping(path = "/transactions/{id}")
    public ResponseEntity<TransactionDto> fullUpdateTransaction(
            @PathVariable("id")Long id,
            @RequestBody TransactionDto transactionDto){
      if(!transactionService.isExists(id)){
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      transactionDto.setId(id);
      TransactionEntity transactionEntity = mapper.mapFrom(transactionDto);
      TransactionEntity savedTransactionEntity =  transactionService.save(transactionEntity);

      return  new ResponseEntity<>(
              mapper.mapTo(savedTransactionEntity),
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

//    @GetMapping(path = "/transactions/{transactionId}/products")
//    public Page<ProductDto> findProductsByTransactionId(@PathVariable("transactionId")Long id, Pageable pageable){
//        Page<ProductEntity> products = productService.findByTransactionId(id, pageable );
//        return products.map(productEntity -> productMapper.mapTo(productEntity));
//    }

//    @DeleteMapping(path = "/products/{transactionId}/{productId}")
//    public ResponseEntity deleteProductFromTransaction(@PathVariable("transactionId")Long tid,
//                                        @PathVariable("productId")Long pid){
//        productService.deleteProductFromTransaction(tid,pid);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }



//    @GetMapping(path = "products/{transactionId}")
//    public List<ProductDto> findProductsByTransactionId(@PathVariable("transactionId")Long id){
//        List<ProductEntity> products = productService.findByTransactionId(id);
//        return products.stream().map(productEntity -> productMapper.mapTo(productEntity))
//                .collect(Collectors.toList());
//
//    }

}
