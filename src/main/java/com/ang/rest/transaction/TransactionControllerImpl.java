package com.ang.rest.transaction;

import com.ang.rest.domain.dto.*;
import com.ang.rest.domain.entity.Product;
import com.ang.rest.domain.entity.Shop;
import com.ang.rest.domain.entity.Transaction;
import com.ang.rest.domain.entity.TransactionDetails;
import com.ang.rest.mappers.impl.TransactionDetailsMapper;
import com.ang.rest.mappers.impl.TransactionMapper;
import com.ang.rest.product.ProductService;
import com.ang.rest.shop.ShopService;

import com.ang.rest.transaction_details.TransactionDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/transactions")
@RequiredArgsConstructor
public class TransactionControllerImpl implements TransactionController {

    private final TransactionService transactionService;
    private final ProductService productService;
    private final TransactionDetailsService transactionDetailsService;
    private final ShopService shopService;
    private final TransactionMapper transactionMapper;
    private final TransactionDetailsMapper transactionDetailsMapper;





    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto) {
        Transaction transaction = transactionMapper.mapToEntity(transactionDto);
        Shop shop = shopService.findByName(transactionDto.getShopName());
        transaction.setShop(shop);
        Transaction savedTransaction = transactionService.save(transaction);
        return new ResponseEntity<>(transactionMapper.mapToDto(savedTransaction), HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAll();
        return transactions.stream().map(transaction -> {
            TransactionDto transactionDto = transactionMapper.mapToDto(transaction);
            transactionDto.setShopName(transaction.getShop().getName());
            return transactionDto;
        }).collect(Collectors.toList());
    }

    @GetMapping
    public Page<TransactionDto> getTransactions(Pageable pageable) {
        Page<Transaction> transactions = transactionService.findAll(pageable);
        return transactions.map(transaction -> transactionMapper.mapToDto(transaction));
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable("id") Long id) {
        Transaction transaction = transactionService.findOne(id);
        TransactionDto transactionDto = transactionMapper.mapToDto(transaction);
        return new ResponseEntity<>(transactionDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id") Long id) {

        transactionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{id}/details/all")
    public ResponseEntity<List<TransactionDetailsDto>> getAllTransactionDetails(@PathVariable Long id) {
        List<TransactionDetails> transactionDetails = transactionDetailsService.getTransactionDetailsByTransactionId(id);
        List<TransactionDetailsDto> transactionDetailsDtos = transactionDetails.stream().map(transactionDetailsMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(transactionDetailsDtos);
    }

    @GetMapping("/id/{id}/details")
    public Page<TransactionDetailsDto> getTransactionDetailsByTransactionId(@PathVariable Long id, Pageable pageable) {
        Page<TransactionDetails> transactionDetails = transactionDetailsService.getTransactionDetailsByTransactionId(id, pageable);
        return transactionDetails.map(transactionDetailsMapper::mapToDto);
    }

    @PostMapping("/id/{id}/product")
    public ResponseEntity<Void> addProductToTransaction(@PathVariable Long id, @RequestBody ProductDetailsDto productDetailsDto) {
        Transaction transaction = transactionService.findOne(id);
        Product product = productService.findOne(productDetailsDto.getProductId());

        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setTransaction(transaction);
        transactionDetails.setProduct(product);
        transactionDetails.setPrice(productDetailsDto.getPrice());
        transactionDetails.setQuantity(productDetailsDto.getQuantity());
        transactionDetailsService.save(transactionDetails);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping(path = "/id/{id}/product/{productName}")
    public ResponseEntity<Void> deleteProductFromTransaction(@PathVariable("id") Long transactionId, @PathVariable("productName") String productName) {
        if (!transactionService.isExists(transactionId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        transactionDetailsService.deleteProduct(transactionId, productName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}