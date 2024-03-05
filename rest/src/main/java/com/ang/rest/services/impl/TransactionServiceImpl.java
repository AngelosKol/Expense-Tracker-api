package com.ang.rest.services.impl;

import com.ang.rest.domain.entities.TransactionEntity;
import com.ang.rest.repositories.TransactionRepository;
import com.ang.rest.services.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionEntity save(TransactionEntity transaction) {
        return this.transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionEntity> findAll() {
        return null;
    }

    @Override
    public Optional<TransactionEntity> findOne(Long id) {
        return Optional.empty();
    }
}
