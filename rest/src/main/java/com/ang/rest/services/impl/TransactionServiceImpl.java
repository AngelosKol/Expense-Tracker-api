package com.ang.rest.services.impl;

import com.ang.rest.domain.entities.ProductEntity;
import com.ang.rest.domain.entities.TransactionEntity;
import com.ang.rest.repositories.ProductRepository;
import com.ang.rest.repositories.TransactionRepository;
import com.ang.rest.services.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TransactionServiceImpl implements TransactionService {

    private  ProductRepository productRepository;
    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, ProductRepository productRepository) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
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

    @Override
    public boolean isExists(Long id){
        return transactionRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {

    }

//    @Override
//    @Transactional
//    public void delete(Long id) {
//        List<ProductEntity> productsToDelete = productRepository.findByTransactionId(id);
//        // Delete each related product
//        for (ProductEntity product : productsToDelete) {
//            productRepository.delete(product);
//        }
//
//        transactionRepository.deleteById(id);
//    }
}
