package com.ang.rest.transaction_details;

import com.ang.rest.domain.dto.*;
import com.ang.rest.domain.entity.TransactionDetails;
import io.quarkus.panache.common.Page;

import java.util.List;


public interface TransactionDetailsService {
    List<TransactionDetailsDTO> findAllTransactionDetailsByTransactionId(Long id);
    List<TransactionDetailsDTO> findTransactionDetailsByTransactionId(Long id, Page page);

    void addProductToTransaction(Long transactionId, ProductDetailsDTO productDetailsDto);
    void addProductsBatch(Long id, List<ProductDetailsDTO> productDetailsDto);

    void save(TransactionDetails transactionDetails);

    void deleteProduct(Long transactionId, String productName);

    boolean checkIfProductExists(Long productId);

    void ensureProductNotInTransaction(Long productId);

}
