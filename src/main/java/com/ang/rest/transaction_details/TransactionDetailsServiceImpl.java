package com.ang.rest.transaction_details;

import com.ang.rest.auth.AuthenticatedUserUtil;
import com.ang.rest.domain.dto.*;
import com.ang.rest.domain.entity.Product;
import com.ang.rest.domain.entity.Transaction;
import com.ang.rest.domain.entity.TransactionDetails;
import com.ang.rest.domain.entity.User;
import com.ang.rest.exception.ResourceConflictException;
import com.ang.rest.exception.ResourceNotFoundException;
import com.ang.rest.mapper.impl.TransactionDetailsMapper;
import com.ang.rest.product.ProductService;
import com.ang.rest.transaction.TransactionService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class TransactionDetailsServiceImpl implements TransactionDetailsService {

    @Inject TransactionDetailsRepository transactionDetailsRepository;
    @Inject AuthenticatedUserUtil authenticatedUserUtil;
    @Inject TransactionService transactionService;
    @Inject ProductService productService;
    @Inject TransactionDetailsMapper transactionDetailsMapper;

    @Transactional
    @Override
    public void addProductToTransaction(Long transactionId, ProductDetailsDTO productDetailsDto) {
        User authenticatedUser = authenticatedUserUtil.getAuthenticatedUser();
        Transaction transaction = transactionService.findOne(transactionId, authenticatedUser.getId());
        Product product = productService.findOneEntity(productDetailsDto.productId());
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setTransaction(transaction);
        transactionDetails.setProduct(product);
        transactionDetails.setPrice(productDetailsDto.price());
        transactionDetails.setQuantity(productDetailsDto.quantity());
        transactionDetailsRepository.persist(transactionDetails);
    }

    @Override
    @Transactional
    public void addProductsBatch(Long transactionId, List<ProductDetailsDTO> productDetailsList) {
        User authenticatedUser = authenticatedUserUtil.getAuthenticatedUser();
        Transaction transaction = transactionService.findOne(transactionId, authenticatedUser.getId());
        Map<Long, ProductDetailsDTO> productMap = productDetailsList.stream()
                .collect(Collectors.toMap(ProductDetailsDTO::productId, dto -> dto));
        List<Long> productIDList = new ArrayList<>(productMap.keySet());
        List<Product> fetchedProducts = productService.findAllByID(productIDList);
        List<TransactionDetails> transactionDetails = fetchedProducts.stream()
                .map(product -> TransactionDetails.builder()
                        .transaction(transaction)
                        .product(product)
                        .price(productMap.get(product.getId()).price())
                        .quantity(productMap.get(product.getId()).quantity())
                        .build())
                .collect(Collectors.toList());
        transactionDetailsRepository.persist(transactionDetails);
    }

    @Override
    public void save(TransactionDetails transactionDetails) {
        transactionDetailsRepository.persist(transactionDetails);
    }

    @Override
    @Transactional
    public void deleteProduct(Long transactionId, String productName) {
        transactionDetailsRepository.removeProductFromTransaction(transactionId, productName);
    }

    @Override
    public List<TransactionDetailsDTO> findAllTransactionDetailsByTransactionId(Long id) {
        PanacheQuery<TransactionDetails> transactionDetails = transactionDetailsRepository.findByTransactionId(id);
        if(transactionDetails == null) {
            throw  new ResourceNotFoundException("Transaction with " + id + "not found.");
        }
        return transactionDetails.stream().map(transactionDetailsMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<TransactionDetailsDTO> findTransactionDetailsByTransactionId(Long id, Page page) {
        PanacheQuery<TransactionDetails> query = transactionDetailsRepository.findByTransactionId(id);
        return query.page(page).stream().map(transactionDetailsMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public boolean checkIfProductExists(Long productId) {
        return transactionDetailsRepository.existsByProduct_id(productId);
    }

    @Override
    @Transactional
    public void ensureProductNotInTransaction(Long productId)  {
        if (transactionDetailsRepository.existsByProduct_id(productId)) {
            throw new ResourceConflictException("This product exists in a transaction. Please remove the product from the associated transaction first.");
        }
    }


}
