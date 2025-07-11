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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TransactionDetailsServiceImpl implements TransactionDetailsService {
    private static final Logger logger =  LoggerFactory.getLogger(TransactionDetailsService.class);

    private final TransactionDetailsRepository transactionDetailsRepository;
    private final AuthenticatedUserUtil authenticatedUserUtil;
    private final TransactionService transactionService;
    private final ProductService productService;
    private final TransactionDetailsMapper transactionDetailsMapper;

    @Transactional
    @Override
    public void addProductToTransaction(Long transactionId, ProductDetailsDTO productDetailsDto) {
        User authenticatedUser = authenticatedUserUtil.getAuthenticatedUser();
        Transaction transaction = transactionService.findOne(transactionId, authenticatedUser.getId());
        Product product = productService.findOneEntity(productDetailsDto.productId());
       TransactionDetails transactionDetails = toDetails(transaction, product, productDetailsDto);
        transactionDetailsRepository.save(transactionDetails);
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
                .map(product -> {
                    ProductDetailsDTO productDetailsDTO = productMap.get(product.getId());
                    return toDetails(transaction, product, productDetailsDTO);

                })
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
    public List<TransactionDetailsDTO> getTransactionDetailsByTransactionId(Long id) {
        List<TransactionDetails>  transactionDetails =
                transactionDetailsRepository.findByTransactionId(id).
                        orElseThrow(() -> new EntityNotFoundException("Transaction with " + id + "not found."));
        return transactionDetails.stream().map(transactionDetailsMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<TransactionDetailsDTO> getTransactionDetailsByTransactionId(Long id, Pageable pageable) {
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
    private TransactionDetails toDetails(Transaction t, Product p, ProductDetailsDTO dto) {
        BigDecimal rawAmount = dto.quantity();
        BigDecimal factor = BigDecimal.valueOf(p.getMeasuringType().getDefaultConversionFactor());
        BigDecimal baseQty = rawAmount.divide(factor, 3, RoundingMode.HALF_UP);
        BigDecimal pricePerBase = dto.price().divide(baseQty, 2, RoundingMode.HALF_UP);
        return TransactionDetails.builder()
                .transaction(t)
                .product(p)
                .price(pricePerBase)
                .quantity(baseQty)
                .build();
    }
}
