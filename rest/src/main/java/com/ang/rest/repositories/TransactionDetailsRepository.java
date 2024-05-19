package com.ang.rest.repositories;

import com.ang.rest.domain.dto.AnalyticsDto;
import com.ang.rest.domain.entities.Transaction;
import com.ang.rest.domain.entities.TransactionDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionDetailsRepository extends CrudRepository<TransactionDetails, Long>,
        PagingAndSortingRepository<TransactionDetails, Long> {
    List<TransactionDetails> findByTransactionId(Long transactionId);

    Page<TransactionDetails>  findByTransactionId(Long transactionId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM TransactionDetails td WHERE td.transaction.id = :transactionId AND td.product.id IN (SELECT p.id FROM Product p WHERE p.name = :name)")
    void removeProductFromTransaction(@Param("transactionId") Long transactionId, @Param("name") String name);


    @Query("SELECT new com.ang.rest.domain.dto.AnalyticsDto(s.name, TO_CHAR(t.date, 'YYYY-MM-DD'), SUM(td.price * td.quantity)) " +
            "FROM TransactionDetails td " +
            "INNER JOIN td.transaction t " +
            "INNER JOIN t.shop s " +
            "WHERE t.date >= :fromDate AND t.date <= :toDate " +
            "GROUP BY s.name, t.date")
    List<AnalyticsDto> getTotalSpent(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
