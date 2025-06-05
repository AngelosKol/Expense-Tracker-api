package com.ang.rest.transaction_details;

import com.ang.rest.domain.dto.TransactionDetailsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TransactionDetailsController {
    @Operation(summary = "Get all transaction details", description = "Retrieve all details for a specific transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved transaction details", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @GetMapping("/id/{id}/all")
    ResponseEntity<List<TransactionDetailsDTO>> getAllTransactionDetails(@PathVariable Long id);

    @Operation(summary = "Get paginated transaction details", description = "Retrieve paginated details for a specific transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated transaction details", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @GetMapping("/id/{id}")
    Page<TransactionDetailsDTO> getTransactionDetailsByTransactionId(@PathVariable Long id, Pageable pageable);

    @Operation(summary = "Delete product from transaction", description = "Delete a product from an existing transaction by product name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product removed from transaction successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction or product not found")
    })
    @DeleteMapping(path = "/id/{id}/product/{productName}")
    ResponseEntity<Void> deleteProductFromTransaction(@PathVariable("id") Long transactionId, @PathVariable("productName") String productName);

}
