package com.ang.rest.transaction;

import com.ang.rest.domain.dto.TransactionDTO;
import com.ang.rest.domain.entity.Shop;
import com.ang.rest.domain.entity.Transaction;
import com.ang.rest.domain.entity.User;
import com.ang.rest.exception.ResourceNotFoundException;
import com.ang.rest.mapper.impl.TransactionMapper;
import com.ang.rest.shop.ShopService;
import com.ang.rest.transaction_details.TransactionDetailsRepository;
import com.ang.rest.user.UserService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TransactionServiceImpl implements TransactionService {

    @Inject TransactionRepository transactionRepository;
    @Inject ShopService shopService;
    @Inject TransactionDetailsRepository tDetailsRepository;
    @Inject TransactionMapper transactionMapper;
    @Inject UserService userService;

    @Override
    public Transaction save(TransactionDTO transactionDto) {
        User authenticatedUser = userService.findByEmail();
        Shop shop = shopService.findByName(transactionDto.shopName());
        Transaction transaction = transactionMapper.mapToEntity(transactionDto, authenticatedUser, shop);
        transactionRepository.persist(transaction);
        return transaction;
    }

    @Override
    public List<TransactionDTO> findAll() {
        return transactionRepository.findAll().stream().map(transactionMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findAll(Page page) {
        User authenticatedUser = userService.findByEmail();
        PanacheQuery<Transaction> query = transactionRepository.findAllByUserId(authenticatedUser.id);
        return query.page(page).stream().map(transactionMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public TransactionDTO findOne(Long transactionId) {
        User authenticatedUser = userService.findByEmail();
        return transactionRepository.findByIdAndUserId(transactionId, authenticatedUser.id)
                .singleResultOptional()
                .map(transactionMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
    }

    @Override
    public Transaction findOne(Long transactionId, Long userId) {
        User authenticatedUser = userService.findByEmail();
        return transactionRepository.findByIdAndUserId(transactionId, authenticatedUser.id)
                .singleResultOptional()
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new ResourceNotFoundException(" Transaction with id " + id + " Not found");
        }
        tDetailsRepository.deleteByTransactionId(id);
        transactionRepository.deleteById(id);
    }
    @Override
    public boolean existsById(Long id) {
        return transactionRepository.existsById(id);
    }

}
