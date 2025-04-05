package com.ang.rest.transaction_details;

import com.ang.rest.domain.dto.*;
import com.ang.rest.domain.entity.TransactionDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TransactionDetailsService {
    List<TransactionDetailsDTO> getTransactionDetailsByTransactionId(Long id);
    Page<TransactionDetailsDTO> getTransactionDetailsByTransactionId(Long id, Pageable pageable);

    void addProductToTransaction(Long transactionId, ProductDetailsDTO productDetailsDto);
    void addProductsBatch(Long id, List<ProductDetailsDTO> productDetailsDto);

    void save(TransactionDetails transactionDetails);

    void deleteProduct(Long transactionId, String productName);

    boolean checkIfProductExists(Long productId);

    void ensureProductNotInTransaction(Long productId) throws DataIntegrityViolationException;

}
