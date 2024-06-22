package com.ang.rest.services;

import com.ang.rest.domain.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {
    Transaction save(Transaction transaction);

    List<Transaction> findAll();

    Page<Transaction> findAll(Pageable pageable);

    Transaction findOne(Long id);
//    Transaction findOne(Long id);

    boolean isExists(Long id);

    void delete(Long id);


}
