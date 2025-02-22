package com.ang.rest.transaction;
import com.ang.rest.auth.AuthenticatedUserUtil;
import com.ang.rest.domain.dto.TransactionDto;
import com.ang.rest.domain.entity.Shop;
import com.ang.rest.domain.entity.User;
import com.ang.rest.exception.ResourceNotFoundException;
import com.ang.rest.domain.entity.Transaction;
import com.ang.rest.domain.entity.TransactionDetails;
import com.ang.rest.mapper.impl.TransactionMapper;
import com.ang.rest.shop.ShopService;
import com.ang.rest.transaction_details.TransactionDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    public boolean isExists(Long id) {
        return transactionRepository.existsById(id);
    }


    @Override
    public Transaction save(TransactionDto transactionDto) {
        User authenticatedUser = authenticatedUserUtil.getAuthenticatedUser();
        Transaction transaction = transactionMapper.mapToEntity(transactionDto);
        Shop shop = shopService.findByName(transactionDto.getShopName());
        transaction.setShop(shop);
        transaction.setUser(authenticatedUser);
        return transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDto> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        List<Transaction> transactions = transactionRepository.findAllByUserId(authenticatedUser.getId());
        return transactions.stream().map( t -> {
            TransactionDto transactionDto = transactionMapper.mapToDto(t);
            transactionDto.setShopName(t.getShop().getName());
            return transactionDto;
        }).collect(Collectors.toList());
    }

    @Override
    public Page<TransactionDto> findAll(Pageable pageable) {
        User authenticatedUser = authenticatedUserUtil.getAuthenticatedUser();
       Page<Transaction> transactions = transactionRepository.findAllByUserId(authenticatedUser.getId(), pageable);
        return transactions.map(transactionMapper::mapToDto);
    }

    @Override
    public TransactionDto findOne(Long transactionId) {
        User authenticatedUser = authenticatedUserUtil.getAuthenticatedUser();
        return transactionRepository.findByIdAndUserId(transactionId, authenticatedUser.getId()).map(transactionMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
    }

   @Override
   public Transaction findOne(Long transactionId, Long userId) {
       User authenticatedUser = authenticatedUserUtil.getAuthenticatedUser();
       return transactionRepository.findByIdAndUserId(transactionId,authenticatedUser.getId()).orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
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
