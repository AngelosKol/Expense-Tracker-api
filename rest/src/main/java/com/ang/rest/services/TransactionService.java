package com.ang.rest.services;

import com.ang.rest.domain.entities.TransactionEntity;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    TransactionEntity save(TransactionEntity transaction);

    List<TransactionEntity> findAll();

    Optional<TransactionEntity> findOne(Long id);
}
