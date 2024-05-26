package com.ang.rest.services;

import com.ang.rest.domain.dto.AnalyticsDto;
import com.ang.rest.domain.dto.YearlyCostDto;
import com.ang.rest.domain.entities.TransactionDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;


public interface TransactionDetailsService {

    List<TransactionDetails> getTransactionDetailsByTransactionId(Long id);


    void save(TransactionDetails transactionDetails);


    void deleteProduct(Long transactionId, String productName);

    Page<TransactionDetails> getTransactionDetailsByTransactionId(Long id, Pageable pageable);


    List<AnalyticsDto> getTotalSpentByDate(Date fromDate, Date toDate);


    List<Object> getMonthlyCosts(String year);
}
