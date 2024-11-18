package com.ang.rest.transaction_details;

import com.ang.rest.domain.dto.AnalyticsDto;
import com.ang.rest.domain.dto.MonthCostDto;
import com.ang.rest.domain.dto.YearCostsDto;
import com.ang.rest.domain.entity.TransactionDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public interface TransactionDetailsService {

    List<TransactionDetails> getTransactionDetailsByTransactionId(Long id);

    void save(TransactionDetails transactionDetails);

    void deleteProduct(Long transactionId, String productName);

    Page<TransactionDetails> getTransactionDetailsByTransactionId(Long id, Pageable pageable);

    List<AnalyticsDto> getTotalSpentByDate(Date fromDate, Date toDate);

    List<YearCostsDto> getYearTotals(int year);

    List<MonthCostDto> getMonthTotalsWithShop(int year, int month);

    boolean checkIfProductExists(Long productId);

    void ensureProductNotInTransaction(Long productId) throws DataIntegrityViolationException;
}
