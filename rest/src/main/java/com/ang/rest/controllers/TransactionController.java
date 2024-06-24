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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
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



    @Operation(summary = "Create a transaction", description = "Create a new transaction with associated shop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))),
            @ApiResponse(responseCode = "404", description = "Shop not found", content = @Content(mediaType = "application/json")),

    })
    @PostMapping(path = "/transactions")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto) {
        Transaction transaction = transactionMapper.mapFrom(transactionDto);
        Shop shop = shopService.findByName(transactionDto.getShopName());
        transaction.setShop(shop);
        Transaction savedTransaction = transactionService.save(transaction);
        return new ResponseEntity<>(transactionMapper.mapTo(savedTransaction), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all transactions", description = "Retrieve all transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class)))
    })
    @GetMapping(path = "/transactions/all")
    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAll();
        return transactions.stream()
                .map(transaction -> {
                    TransactionDto transactionDto = transactionMapper.mapTo(transaction);
                    transactionDto.setShopName(transaction.getShop().getName());
                    return transactionDto;
                })
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get transactions by page", description = "Retrieve transactions in a paginated format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated transactions", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class)))
    })
    @GetMapping(path = "/transactions")
    public Page<TransactionDto> getTransactions(Pageable pageable) {
        Page<Transaction> transactions = transactionService.findAll(pageable);
        return transactions.map(transaction -> transactionMapper.mapTo(transaction));
    }

    @Operation(summary = "Get a specific transaction", description = "Retrieve a transaction by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the transaction", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @GetMapping(path = "/transactions/id/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable("id") Long id) {
        Transaction transaction = transactionService.findOne(id);
        TransactionDto transactionDto = transactionMapper.mapTo(transaction);
        return new ResponseEntity<>(transactionDto, HttpStatus.OK);
    }

    @Operation(summary = "Delete a transaction", description = "Delete a transaction by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transaction deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @DeleteMapping(path = "/transactions/id/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id") Long id) {

        transactionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get all transaction details", description = "Retrieve all details for a specific transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved transaction details", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDetailsDto.class))),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @GetMapping("/transactions/id/{id}/details/all")
    public ResponseEntity<List<TransactionDetailsDto>> getAllTransactionDetails(@PathVariable Long id) {
        List<TransactionDetails> transactionDetails = transactionDetailsService.getTransactionDetailsByTransactionId(id);
        List<TransactionDetailsDto> transactionDetailsDtos = transactionDetails.stream()
                .map(transactionDetailsMapper::mapTo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactionDetailsDtos);
    }

    @Operation(summary = "Get paginated transaction details", description = "Retrieve paginated details for a specific transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated transaction details", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDetailsDto.class))),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @GetMapping("/transactions/id/{id}/details")
    public Page<TransactionDetailsDto> getTransactionDetailsByTransactionId(@PathVariable Long id, Pageable pageable) {
        Page<TransactionDetails> transactionDetails = transactionDetailsService.getTransactionDetailsByTransactionId(id, pageable);
        return transactionDetails.map(transactionDetailsMapper::mapTo);
    }

    @Operation(summary = "Add product to transaction", description = "Add a product to an existing transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product added to transaction successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction or product not found")
    })
    @PostMapping("transactions/id/{id}/product")
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

    @Operation(summary = "Delete product from transaction", description = "Delete a product from an existing transaction by product name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product removed from transaction successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction or product not found")
    })
    @DeleteMapping(path = "/transactions/id/{id}/product/{productName}")
    public ResponseEntity<Void> deleteProductFromTransaction(@PathVariable("id") Long transactionId, @PathVariable("productName") String productName) {
        if (!transactionService.isExists(transactionId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        transactionDetailsService.deleteProduct(transactionId, productName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}