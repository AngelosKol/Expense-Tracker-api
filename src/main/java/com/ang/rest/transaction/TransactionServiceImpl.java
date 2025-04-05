package com.ang.rest.transaction;

import com.ang.rest.auth.AuthenticatedUserUtil;
import com.ang.rest.domain.dto.TransactionDTO;
import com.ang.rest.domain.entity.Shop;
import com.ang.rest.domain.entity.Transaction;
import com.ang.rest.domain.entity.User;
import com.ang.rest.exception.ResourceNotFoundException;
import com.ang.rest.mapper.impl.TransactionMapper;
import com.ang.rest.shop.ShopService;
import com.ang.rest.transaction_details.TransactionDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ShopService shopService;
    private final TransactionDetailsRepository tDetailsRepository;
    private final TransactionMapper transactionMapper;
    private final AuthenticatedUserUtil authenticatedUserUtil;

    @Override
    public Transaction save(TransactionDTO transactionDto) {
        User authenticatedUser = authenticatedUserUtil.getAuthenticatedUser();
        Shop shop = shopService.findByName(transactionDto.shopName());
        Transaction transaction = transactionMapper.mapToEntity(transactionDto, authenticatedUser, shop);
        return transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDTO> findAll() {
        User authenticatedUser = authenticatedUserUtil.getAuthenticatedUser();
        List<Transaction> transactions = transactionRepository.findAllByUserId(authenticatedUser.getId());
        return transactions.stream().map(transactionMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public Page<TransactionDTO> findAll(Pageable pageable) {
        User authenticatedUser = authenticatedUserUtil.getAuthenticatedUser();
        Page<Transaction> transactions = transactionRepository.findAllByUserId(authenticatedUser.getId(), pageable);
        return transactions.map(transactionMapper::mapToDto);
    }

    @Override
    public TransactionDTO findOne(Long transactionId) {
        User authenticatedUser = authenticatedUserUtil.getAuthenticatedUser();
        return transactionRepository.findByIdAndUserId(transactionId, authenticatedUser.getId()).map(transactionMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
    }

    @Override
    public Transaction findOne(Long transactionId, Long userId) {
        User authenticatedUser = authenticatedUserUtil.getAuthenticatedUser();
        return transactionRepository.findByIdAndUserId(transactionId, authenticatedUser.getId()).orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
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

    @Override
    public boolean isExists(Long id) {
        return transactionRepository.existsById(id);
    }

}
