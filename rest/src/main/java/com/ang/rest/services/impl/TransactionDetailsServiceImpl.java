package com.ang.rest.services.impl;

import com.ang.rest.domain.dto.TransactionDetailsDto;
import com.ang.rest.domain.entities.TransactionDetails;
import com.ang.rest.repositories.TransactionDetailsRepository;
import com.ang.rest.services.TransactionDetailsService;
import com.ang.rest.services.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



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




}
