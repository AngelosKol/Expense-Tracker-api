package com.ang.rest.transaction_details;

import com.ang.rest.auth.AuthenticatedUserUtil;
import com.ang.rest.domain.dto.*;
import com.ang.rest.domain.entity.Product;
import com.ang.rest.domain.entity.Transaction;
import com.ang.rest.domain.entity.TransactionDetails;
import com.ang.rest.domain.entity.User;
import com.ang.rest.mapper.impl.TransactionDetailsMapper;
import com.ang.rest.product.ProductService;
import com.ang.rest.transaction.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TransactionDetailsServiceImpl implements TransactionDetailsService {

    private final TransactionDetailsRepository transactionDetailsRepository;
    private final AuthenticatedUserUtil authenticatedUserUtil;
    private final TransactionService transactionService;
    private final ProductService productService;
    private final TransactionDetailsMapper transactionDetailsMapper;

    @Transactional
    @Override
    public void addProductToTransaction(Long transactionId, ProductDetailsDto productDetailsDto) {
        User authenticatedUser = authenticatedUserUtil.getAuthenticatedUser();
        Transaction transaction = transactionService.findOne(transactionId, authenticatedUser.getId());
        Product product = productService.findOneEntity(productDetailsDto.getProductId());
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setTransaction(transaction);
        transactionDetails.setProduct(product);
        transactionDetails.setPrice(productDetailsDto.getPrice());
        transactionDetails.setQuantity(productDetailsDto.getQuantity());
        transactionDetailsRepository.save(transactionDetails);
    }

    @Override
    @Transactional
    public void addProductsBatch(Long transactionId, List<ProductDetailsDto> productDetailsList) {
        User authenticatedUser = authenticatedUserUtil.getAuthenticatedUser();
        Transaction transaction = transactionService.findOne(transactionId, authenticatedUser.getId());
        Map<Long, ProductDetailsDto> productMap = productDetailsList.stream()
                .collect(Collectors.toMap(ProductDetailsDto::getProductId, dto -> dto));
        List<Long> productIDList = new ArrayList<>(productMap.keySet());
        List<Product> fetchedProducts = productService.findAllByID(productIDList);
        List<TransactionDetails> transactionDetails = fetchedProducts.stream()
                .map(product -> TransactionDetails.builder()
                        .transaction(transaction)
                        .product(product)
                        .price(productMap.get(product.getId()).getPrice())
                        .quantity(productMap.get(product.getId()).getQuantity())
                        .build())
                .collect(Collectors.toList());
        transactionDetailsRepository.saveAllAndFlush(transactionDetails);
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
    public List<TransactionDetailsDto> getTransactionDetailsByTransactionId(Long id) {
        List<TransactionDetails>  transactionDetails =
                transactionDetailsRepository.findByTransactionId(id).
                        orElseThrow(() -> new EntityNotFoundException("Transaction with " + id + "not found."));
        return transactionDetails.stream().map(transactionDetailsMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<TransactionDetailsDto> getTransactionDetailsByTransactionId(Long id, Pageable pageable) {
        return transactionDetailsRepository.findByTransactionId(id, pageable)
                .map(transactionDetailsMapper::mapToDto);
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
