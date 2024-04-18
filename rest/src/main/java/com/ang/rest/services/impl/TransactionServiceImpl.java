package com.ang.rest.services.impl;

import com.ang.rest.domain.dto.ProductRequest;
import com.ang.rest.domain.entities.Product;
import com.ang.rest.domain.entities.Transaction;
import com.ang.rest.repositories.ProductRepository;
import com.ang.rest.repositories.TransactionRepository;
import com.ang.rest.services.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findAll() {
     return  StreamSupport.stream(transactionRepository.
                     findAll()
                     .spliterator(),
                     false)
              .collect(Collectors.toList());
    }

    @Override
    public Optional<Transaction> findOne(Long id) {
     return    transactionRepository.findById(id);
    }


    public void addProductToTransaction(Long transactionId, ProductRequest request) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        Optional<Product> optionalProduct = productRepository.findById(request.getProductId());
        if (optionalTransaction.isPresent() && optionalProduct.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            Product product = optionalProduct.get();
            product.setPrice(request.getPrice());
            transaction.getProducts().add(product);
            transactionRepository.save(transaction);
        } else {
            throw new RuntimeException("Transaction or Product not found");
        }
    }




    public Set<Product> getProducts2(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            System.out.println("Transaction IS " + transaction.get());
            var products = transaction.get().getProducts();
            System.out.println("Products are " + products);
            System.out.print("Found " + products.size() + " products for transaction with ID: " + id + "\n");
            return products;
        } else {
            return Collections.emptySet();
        }
    }

    @Override
    public Set<Product> getProducts(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Not found"));
        System.out.println("Transaction IS " + transaction);
        var products = transaction.getProducts();
        System.out.println("Products are " + products);
        System.out.print("Found " + products.size() + " products for transaction with ID: " + id + "\n");
        System.out.println(products);
        return products;
    }


    @Override
    public boolean isExists(Long id){
        return transactionRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Not found"));

        transactionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteProductFromTransaction(Long tid, Long pid) {
        Transaction transaction = transactionRepository.findById(tid)
                .orElseThrow(()-> new EntityNotFoundException("Not found"));

        Product productToRemove = null;
        for(Product product : transaction.getProducts()){
            if(product.getId().equals(pid)){
                productToRemove = product;
                break;
            }
        }
        if(productToRemove != null){
            transaction.getProducts().remove(productToRemove);
            transactionRepository.save(transaction);
        }else{
            throw new EntityNotFoundException("Product not found");
        }
    }

}
