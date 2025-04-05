package com.ang.rest.transaction;

import com.ang.rest.domain.dto.TransactionDTO;
import com.ang.rest.domain.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {
    Transaction save(TransactionDTO transactionDto);


    List<TransactionDTO> findAll();

    Page<TransactionDTO> findAll(Pageable pageable);

    TransactionDTO findOne(Long id);
    Transaction findOne(Long id, Long userId);

    boolean isExists(Long id);

    void delete(Long id);


    void ensureShopNotInTransaction(Long shopId);
}
