package com.ang.rest.services;

import com.ang.rest.domain.dto.TransactionDetailsDto;
import com.ang.rest.domain.entities.Product;
import com.ang.rest.domain.entities.Transaction;
import com.ang.rest.domain.entities.TransactionDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TransactionService {
    Transaction save(Transaction transaction);

    List<Transaction> findAll();

    Page<Transaction> findAll(Pageable pageable);

    Optional<Transaction> findOne(Long id);
//    Transaction findOne(Long id);

    boolean isExists(Long id);

    void delete(Long id);


}
