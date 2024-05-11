package com.ang.rest.services;

import com.ang.rest.domain.dto.TransactionDetailsDto;
import com.ang.rest.domain.entities.TransactionDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface TransactionDetailsService {

    List<TransactionDetails> getTransactionDetailsByTransactionId(Long id);


    void save(TransactionDetails transactionDetails);


    void deleteProduct(Long transactionId, String productName);

    Page<TransactionDetails> getTransactionDetailsByTransactionId(Long id, Pageable pageable);
}
