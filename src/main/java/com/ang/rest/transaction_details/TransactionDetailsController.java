package com.ang.rest.transaction_details;

import com.ang.rest.auth.AuthenticatedUserUtil;
import com.ang.rest.domain.dto.ProductDetailsDto;
import com.ang.rest.mapper.impl.TransactionDetailsMapper;
import com.ang.rest.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transactions")
public class TransactionDetailsController {
    private final TransactionDetailsMapper transactionDetailsMapper;
    private final ProductService productService;
    private final TransactionDetailsService transactionDetailsService;
    private final AuthenticatedUserUtil authenticatedUserUtil;


    @PostMapping("/id/{id}/product")
    public ResponseEntity<Void> addProductToTransaction(@PathVariable Long id, @RequestBody ProductDetailsDto productDetailsDto) {
        transactionDetailsService.addProductToTransaction(id, productDetailsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
