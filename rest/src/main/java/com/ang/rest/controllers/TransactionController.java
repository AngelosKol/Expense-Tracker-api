package com.ang.rest.controllers;

import com.ang.rest.domain.dto.*;
import com.ang.rest.domain.entities.Product;
import com.ang.rest.domain.entities.Shop;
import com.ang.rest.domain.entities.Transaction;
import com.ang.rest.domain.entities.TransactionDetails;
import com.ang.rest.mappers.Mapper;
import com.ang.rest.mappers.impl.ProductMapper;
import com.ang.rest.mappers.impl.TransactionDetailsMapper;
import com.ang.rest.services.ProductService;
import com.ang.rest.services.ShopService;
import com.ang.rest.services.TransactionDetailsService;
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

    private ProductService productService;
    private TransactionDetailsService transactionDetailsService;

    private ShopService shopService;
    private Mapper<Transaction, TransactionPostDto> mapper;

    private Mapper<Transaction, TransactionGetDto> transactionGetMapper;
    private ProductMapper productMapper;

    private TransactionDetailsMapper transactionDetailsMapper;

    public TransactionController(TransactionService transactionService, ProductService productService, ShopService shopService, TransactionDetailsService transactionDetailsService,
                                 Mapper<Transaction, TransactionPostDto> transactionMapper,
                                 Mapper<Transaction, TransactionGetDto> transactionGetMapper,
                                 ProductMapper productMapper, TransactionDetailsMapper transactionDetailsMapper) {
        this.transactionService = transactionService;
        this.shopService = shopService;
        this.productService = productService;
        this.transactionDetailsService = transactionDetailsService;
        this.mapper = transactionMapper;
        this.transactionGetMapper = transactionGetMapper;
        this.productMapper = productMapper;
        this.transactionDetailsMapper = transactionDetailsMapper;
    }


    @PostMapping(path = "/transactions")
    public ResponseEntity<TransactionPostDto> createTransaction(@RequestBody TransactionPostDto transactionPostDto){
        Transaction transaction = mapper.mapFrom(transactionPostDto);
        Optional<Shop> shopOptional = shopService.findOne(transactionPostDto.getShopId());
        Shop shop = shopOptional.orElseThrow(() -> new IllegalArgumentException("Shop not found"));

        transaction.setShop(shop);
        Transaction savedTransaction = transactionService.save(transaction);
        return new ResponseEntity<>(mapper.mapTo(savedTransaction), HttpStatus.CREATED);
    }



    @GetMapping(path = "/transactions")
    public List<TransactionGetDto> getTransactions(){
        List<Transaction> transactions = transactionService.findAll();
        return transactions.stream()
                .map(transaction ->{
                    TransactionGetDto transactionGetDto = transactionGetMapper.mapTo(transaction);
                transactionGetDto.setShopName(transaction.getShop().getName());
                  return transactionGetDto;
                        })
                .collect(Collectors.toList());

    }

    @GetMapping(path = "/transactions/{id}")
    public ResponseEntity<TransactionPostDto> getTransactionById(@PathVariable("id")Long id){
        Optional<Transaction> foundTransaction = transactionService.findOne(id);
        return foundTransaction.map(t -> {
            TransactionPostDto transactionPostDto = mapper.mapTo(t);

            return new ResponseEntity<>(transactionPostDto,HttpStatus.OK);})
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

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

    @PostMapping("transactions/{id}/product")
    public ResponseEntity addProductToTransaction(@PathVariable Long id,
                                                  @RequestBody ProductDetailsDto productDetailsDto){
        Optional<Transaction> transaction = transactionService.findOne(id);
        if(transaction.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Product> product = productService.findOne(productDetailsDto.getProductId());
        if(product.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setTransaction(transaction.get());
        transactionDetails.setProduct(product.get());
        transactionDetails.setPrice(productDetailsDto.getPrice());
        transactionDetails.setQuantity(productDetailsDto.getQuantity());
        transactionDetailsService.save(transactionDetails);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/transactions/{id}/product/{productName}")
    public ResponseEntity deleteProductFromTransaction(@PathVariable("id")Long id, @PathVariable("productName") String productName){
        if(!transactionService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        if(!productService.isExists(productId)){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
        transactionDetailsService.deleteProduct(id, productName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
