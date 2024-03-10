package com.ang.rest.services.impl;

import com.ang.rest.domain.entities.TransactionEntity;
import com.ang.rest.repositories.TransactionRepository;
import com.ang.rest.services.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionEntity save(TransactionEntity transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionEntity> findAll() {
     return  StreamSupport.stream(transactionRepository.
                     findAll()
                     .spliterator(),
                     false)
              .collect(Collectors.toList());
    }

    @Override
    public Optional<TransactionEntity> findOne(Long id) {
     return    transactionRepository.findById(id);
    }
}
