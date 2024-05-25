package com.ang.rest.services.impl;

import com.ang.rest.domain.dto.ProductRequest;
import com.ang.rest.domain.dto.TransactionDetailsDto;
import com.ang.rest.domain.entities.Product;
import com.ang.rest.domain.entities.Transaction;
import com.ang.rest.domain.entities.TransactionDetails;
import com.ang.rest.repositories.ProductRepository;
import com.ang.rest.repositories.TransactionDetailsRepository;
import com.ang.rest.repositories.TransactionRepository;
import com.ang.rest.services.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public  class TransactionServiceImpl implements TransactionService {

    private ProductRepository productRepository;
    private TransactionRepository transactionRepository;

    private  TransactionDetailsRepository tDetailsRepository;
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  ProductRepository productRepository,
                                    TransactionDetailsRepository tDetailsRepository) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.tDetailsRepository  = tDetailsRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findAll() {
        return StreamSupport.stream(transactionRepository.
                                findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Transaction> findOne(Long id) {
        return transactionRepository.findById(id);
    }

    public Optional<List<TransactionDetails>> getTransactionDetailsByTransactionId(Long transactionId) {
        return tDetailsRepository.findByTransactionId(transactionId);
    }

    @Override
    public boolean isExists(Long id) {
        return transactionRepository.existsById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
        transactionRepository.deleteById(id);
    }



}
