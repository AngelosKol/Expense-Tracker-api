package com.ang.rest.services.impl;

import com.ang.rest.domain.dto.AnalyticsDto;
import com.ang.rest.domain.entities.TransactionDetails;
import com.ang.rest.repositories.TransactionDetailsRepository;
import com.ang.rest.services.TransactionDetailsService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class TransactionDetailsServiceImpl implements TransactionDetailsService {

    private final TransactionDetailsRepository transactionDetailsRepository;

    public TransactionDetailsServiceImpl(TransactionDetailsRepository transactionDetailsRepository) {
        this.transactionDetailsRepository = transactionDetailsRepository;
    }


    @Override
    public void save(TransactionDetails transactionDetails) {
        transactionDetailsRepository.save(transactionDetails);
    }

    @Override
    @Transactional
    public void deleteProduct(Long transactionId, String productName) {
        transactionDetailsRepository.removeProductFromTransaction(transactionId, productName);
    }


    @Override
    public List<TransactionDetails> getTransactionDetailsByTransactionId(Long id) {
        return transactionDetailsRepository.findByTransactionId(id).orElseThrow(() -> new EntityNotFoundException("Transaction with " + id + "not found."));
    }

    @Override
    public Page<TransactionDetails> getTransactionDetailsByTransactionId(Long id, Pageable pageable) {
        return transactionDetailsRepository.findByTransactionId(id, pageable);
    }


    @Override
    public List<AnalyticsDto> getTotalSpentByDate(Date fromDate, Date toDate) {
        return transactionDetailsRepository.getTotalSpent(fromDate, toDate);
    }


    @Override
    public List<Object> getYearTotals(int year) {
        return transactionDetailsRepository.getYearTotals(year);
    }


    @Override
    public List<Object> getMonthTotalsWithShop(String year, String month) {
        return transactionDetailsRepository.getMonthTotalWithShop(year, month);
    }

    @Override
    public boolean checkIfProductExists(Long productId) {
        return transactionDetailsRepository.existsByProduct_id(productId);
    }

    @Override
    @Transactional
    public void ensureProductNotInTransaction(Long productId)  {
        if (transactionDetailsRepository.existsByProduct_id(productId)) {
            throw new DataIntegrityViolationException("This product exists in a transaction. Please remove the product from the associated transaction first.");
        }
    }


}
