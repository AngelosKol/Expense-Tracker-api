package com.ang.rest.transaction;


import com.ang.rest.exceptions.ResourceNotFoundException;
import com.ang.rest.domain.entities.Transaction;
import com.ang.rest.domain.entities.TransactionDetails;
import com.ang.rest.transaction_details.TransactionDetailsRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TransactionServiceImpl implements TransactionService {


    private final TransactionRepository transactionRepository;

    private final TransactionDetailsRepository tDetailsRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionDetailsRepository tDetailsRepository) {
        this.transactionRepository = transactionRepository;
        this.tDetailsRepository = tDetailsRepository;
    }

    @Override
    public boolean isExists(Long id) {
        return transactionRepository.existsById(id);
    }


    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findAll() {
        return StreamSupport.stream(transactionRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Page<Transaction> findAll(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }


    @Override
    public Transaction findOne(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
    }

    public Optional<List<TransactionDetails>> getTransactionDetailsByTransactionId(Long transactionId) {
        return tDetailsRepository.findByTransactionId(transactionId);
    }


    @Override
    @Transactional
    public void delete(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(" Transaction with id " + id + " Not found"));

        tDetailsRepository.deleteByTransactionId(id);
        transactionRepository.deleteById(id);
    }


    @Override
    public void ensureShopNotInTransaction(Long shopId) {
        if (transactionRepository.existsByShop_id(shopId)) {
            throw new DataIntegrityViolationException("There is a transaction related with this shop. Please delete the associated transaction in order to delete this shop.");
        }
    }


}
