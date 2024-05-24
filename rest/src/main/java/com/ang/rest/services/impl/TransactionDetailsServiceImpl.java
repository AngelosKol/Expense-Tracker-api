package com.ang.rest.services.impl;

import com.ang.rest.domain.dto.AnalyticsDto;
import com.ang.rest.domain.entities.TransactionDetails;
import com.ang.rest.repositories.TransactionDetailsRepository;
import com.ang.rest.services.TransactionDetailsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class TransactionDetailsServiceImpl implements TransactionDetailsService {

    private TransactionDetailsRepository transactionDetailsRepository;

    public TransactionDetailsServiceImpl(TransactionDetailsRepository transactionDetailsRepository) {
        this.transactionDetailsRepository = transactionDetailsRepository;
    }


    @Override
    public void save(TransactionDetails transactionDetails) {
        transactionDetailsRepository.save(transactionDetails);
    }

    @Override
    public void deleteProduct(Long transactionId, String productName) {
        transactionDetailsRepository.removeProductFromTransaction(transactionId, productName);
    }


    @Override
    public List<TransactionDetails>  getTransactionDetailsByTransactionId(Long id) {
        return  transactionDetailsRepository.findByTransactionId(id);
    }
    @Override
    public Page<TransactionDetails> getTransactionDetailsByTransactionId(Long id, Pageable pageable)   {
        return  transactionDetailsRepository.findByTransactionId(id, pageable);
    }


    @Override
     public List<AnalyticsDto> getTotalSpentByDate(Date fromDate, Date toDate){
        return transactionDetailsRepository.getTotalSpent(fromDate, toDate);
    }






}
