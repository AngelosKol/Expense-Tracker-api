package com.ang.rest.transaction_details;

import com.ang.rest.domain.dto.ProductDetailsDTO;
import com.ang.rest.domain.dto.TransactionDetailsDTO;
import com.ang.rest.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transaction-details")
public class TransactionDetailsControllerImpl {
    private final TransactionDetailsService transactionDetailsService;
    private final TransactionService transactionService;
    @PostMapping("/id/{id}/product")
    public ResponseEntity<Void> addProductToTransaction(@PathVariable Long id, @RequestBody ProductDetailsDTO productDetailsDto) {
        transactionDetailsService.addProductToTransaction(id, productDetailsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/id/{id}/products")
    public ResponseEntity<Void> addProductsBatch(@PathVariable Long id, @RequestBody List<ProductDetailsDTO> productDetailsDto) {
        transactionDetailsService.addProductsBatch(id, productDetailsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}/all")
    public ResponseEntity<List<TransactionDetailsDTO>> getAllTransactionDetails(@PathVariable Long id) {
        return ResponseEntity.ok(transactionDetailsService.getTransactionDetailsByTransactionId(id));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Page<TransactionDetailsDTO>> getTransactionDetailsByTransactionId(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(transactionDetailsService.getTransactionDetailsByTransactionId(id, pageable));
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
