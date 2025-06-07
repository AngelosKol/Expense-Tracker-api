package com.ang.rest.transaction_details;

import com.ang.rest.auth.AuthenticationService;
import com.ang.rest.domain.dto.ProductDetailsDTO;
import com.ang.rest.domain.dto.TransactionDetailsDTO;
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
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class TransactionDetailsServiceImpl implements TransactionDetailsService {

    @Inject
    TransactionDetailsRepository transactionDetailsRepository;
    @Inject
    AuthenticationService authenticationService;
    @Inject
    TransactionService transactionService;
    @Inject
    ProductService productService;
    @Inject
    TransactionDetailsMapper transactionDetailsMapper;

    @Transactional
    @Override
    public void addProductToTransaction(Long transactionId, ProductDetailsDTO productDetailsDto) {
        User authenticatedUser = authenticationService.getAuthenticatedUser();
        Transaction transaction = transactionService.findOne(transactionId, authenticatedUser.id);
        Product product = productService.findOneEntity(productDetailsDto.productId());
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.transaction = transaction;
        transactionDetails.product = product;
        transactionDetails.price = productDetailsDto.price();
        transactionDetails.quantity = productDetailsDto.quantity();
        transactionDetailsRepository.persist(transactionDetails);
    }

    @Override
    @Transactional
    public void addProductsBatch(Long transactionId, List<ProductDetailsDTO> productDetailsList) {
        User authenticatedUser = authenticationService.getAuthenticatedUser();
        Transaction transaction = transactionService.findOne(transactionId, authenticatedUser.id);
        Map<Long, ProductDetailsDTO> productMap = productDetailsList.stream()
                .collect(Collectors.toMap(ProductDetailsDTO::productId, dto -> dto));
        List<Long> productIDList = new ArrayList<>(productMap.keySet());
        List<Product> fetchedProducts = productService.findAllByID(productIDList);
        List<TransactionDetails> transactionDetailsList = fetchedProducts.stream()
                .map(product -> {
                    ProductDetailsDTO dto = productMap.get(product.id);
                    TransactionDetails td = new TransactionDetails();
                    td.transaction = transaction;
                    td.product = product;
                    td.price = dto.price();
                    td.quantity = dto.quantity();
                    return td;
                })
                .collect(Collectors.toList());
        transactionDetailsRepository.persist(transactionDetailsList);
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
        if (transactionDetails == null) {
            throw new ResourceNotFoundException("Transaction with " + id + "not found.");
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
    public void ensureProductNotInTransaction(Long productId) {
        if (transactionDetailsRepository.existsByProduct_id(productId)) {
            throw new ResourceConflictException("This product exists in a transaction. Please remove the product from the associated transaction first.");
        }
    }


}
