package com.ang.rest.transaction;
import com.ang.rest.domain.dto.TransactionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import com.ang.rest.domain.dto.*;

import java.util.List;

public interface TransactionController {

    @Operation(summary = "Create a transaction", description = "Create a new transaction with associated shop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))),
            @ApiResponse(responseCode = "404", description = "Shop not found", content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto);

    @Operation(summary = "Get all transactions", description = "Retrieve all transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class)))
    })
    @GetMapping(path = "/all")
    List<TransactionDto> getAllTransactions();

    @Operation(summary = "Get transactions by page", description = "Retrieve transactions in a paginated format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated transactions", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class)))
    })
    @GetMapping
    Page<TransactionDto> getTransactions(Pageable pageable);

    @Operation(summary = "Get a specific transaction", description = "Retrieve a transaction by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the transaction", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @GetMapping(path = "/id/{id}")
    ResponseEntity<TransactionDto> getTransactionById(@PathVariable("id") Long id);

    @Operation(summary = "Delete a transaction", description = "Delete a transaction by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transaction deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @DeleteMapping(path = "/id/{id}")
    ResponseEntity<Void> deleteTransaction(@PathVariable("id") Long id);

}
