package com.ang.rest.transaction_details;

import com.ang.rest.domain.dto.AnalyticsDto;
import com.ang.rest.domain.dto.MonthCostDto;
import com.ang.rest.domain.dto.YearCostsDto;
import com.ang.rest.domain.entity.TransactionDetails;
import com.ang.rest.domain.entity.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;


@Service
public class TransactionDetailsServiceImpl implements TransactionDetailsService {

    private final TransactionDetailsRepository transactionDetailsRepository;

    public TransactionDetailsServiceImpl(TransactionDetailsRepository transactionDetailsRepository) {
        this.transactionDetailsRepository = transactionDetailsRepository;
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
    public List<TransactionDetails> getTransactionDetailsByTransactionId(Long id) {
        return transactionDetailsRepository.findByTransactionId(id).orElseThrow(() -> new EntityNotFoundException("Transaction with " + id + "not found."));
    }

    @Override
    public Page<TransactionDetails> getTransactionDetailsByTransactionId(Long id, Pageable pageable) {
        return transactionDetailsRepository.findByTransactionId(id, pageable);
    }


    @Override
    public List<AnalyticsDto> getTotalSpentByDate(Date fromDate, Date toDate) {
        return transactionDetailsRepository.getTotalSpent(fromDate, toDate);
    }


    @Override
    public List<YearCostsDto> getYearTotals(int year) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        return transactionDetailsRepository.getYearsTotals(year, authenticatedUser.getId());
    }

    @Override
    public List<MonthCostDto> getMonthTotalsWithShop(int year, int month) {
        LocalDate fromDate = LocalDate.of(year, month, 1);
        LocalDate toDate = YearMonth.from(fromDate).atEndOfMonth();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        return transactionDetailsRepository.getMonthTotalWithShop(fromDate, toDate, authenticatedUser.getId());
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
