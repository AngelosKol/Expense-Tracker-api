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
    private TransactionDetailsService transactionDetailsService;

    private ShopService shopService;

    private Mapper<Transaction, TransactionDto> transactionMapper;
    private ProductMapper productMapper;

    private TransactionDetailsMapper transactionDetailsMapper;

    public TransactionController(TransactionService transactionService, ProductService productService, ShopService shopService, TransactionDetailsService transactionDetailsService,
                                 Mapper<Transaction, TransactionDto> transactionMapper,
                                 ProductMapper productMapper, TransactionDetailsMapper transactionDetailsMapper) {
        this.transactionService = transactionService;
        this.shopService = shopService;
        this.productService = productService;
        this.transactionDetailsService = transactionDetailsService;
        this.transactionMapper = transactionMapper;
        this.productMapper = productMapper;
        this.transactionDetailsMapper = transactionDetailsMapper;
    }


    @PostMapping(path = "/transactions")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto){
        Transaction transaction = transactionMapper.mapFrom(transactionDto);
        Optional<Shop> shopOptional = shopService.findByName(transactionDto.getShopName());
        System.out.println("Shop is : "+ shopOptional);
        Shop shop = shopOptional.orElseThrow(() -> new IllegalArgumentException("Shop not found"));

        transaction.setShop(shop);
        Transaction savedTransaction = transactionService.save(transaction);
        return new ResponseEntity<>(transactionMapper.mapTo(savedTransaction), HttpStatus.CREATED);
    }



    @GetMapping(path = "/transactions")
    public List<TransactionDto> getTransactions(){
        List<Transaction> transactions = transactionService.findAll();
        return transactions.stream()
                .map(transaction ->{
                    TransactionDto transactionDto = transactionMapper.mapTo(transaction);
                transactionDto.setShopName(transaction.getShop().getName());
                  return transactionDto;
                        })
                .collect(Collectors.toList());

    }

    @GetMapping(path = "/transactions/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable("id")Long id){
        Optional<Transaction> foundTransaction = transactionService.findOne(id);
        return foundTransaction.map(t -> {
                    TransactionDto transactionDto = transactionMapper.mapTo(t);

            return new ResponseEntity<>(transactionDto,HttpStatus.OK);})
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }




    @GetMapping("/transactions/{id}/details/all")
    public List<TransactionDetailsDto> getAllTransactionDetails(
            @PathVariable Long id) {
        List<TransactionDetails> transactionDetails =
                transactionDetailsService.getTransactionDetailsByTransactionId(id);

        return transactionDetails.stream().map(transactionDetail ->
                        transactionDetailsMapper.mapTo(transactionDetail))
                .collect(Collectors.toList());
    }

    @GetMapping("/transactions/{id}/details")
    public Page<TransactionDetailsDto> getTransactionDetailsByTransactionId(
            @PathVariable Long id, Pageable pageable) {
        Page<TransactionDetails> transactionDetails =
                transactionDetailsService.getTransactionDetailsByTransactionId(id, pageable);

        return transactionDetails.map(transactionDetail ->
                transactionDetailsMapper.mapTo(transactionDetail));
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
    public ResponseEntity deleteProductFromTransaction(@PathVariable("id")Long transactionId, @PathVariable("productName") String productName){
        if(!transactionService.isExists(transactionId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        if(!productService.isExists(productId)){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
        transactionDetailsService.deleteProduct(transactionId, productName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping(path = "/transactions/{id}")
    public ResponseEntity deleteTransaction(@PathVariable("id")Long id){
        if(!transactionService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        transactionService.delete(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}
