package com.ang.rest.transaction;

import com.ang.rest.domain.dto.TransactionDTO;
import com.ang.rest.domain.entity.Transaction;
import io.quarkus.panache.common.Page;

import java.util.List;

public interface TransactionService {
    Transaction save(TransactionDTO transactionDto);
    List<TransactionDTO> findAll();
    List<TransactionDTO> findAll(Page page);

    TransactionDTO findOne(Long id);
    Transaction findOne(Long id, Long userId);
    void delete(Long id);
}
