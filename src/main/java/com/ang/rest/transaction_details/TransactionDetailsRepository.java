package com.ang.rest.transaction_details;

import com.ang.rest.domain.dto.AnalyticsDto;
import com.ang.rest.domain.dto.MonthCostDto;
import com.ang.rest.domain.dto.YearCostsDto;
import com.ang.rest.domain.entity.TransactionDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long>, PagingAndSortingRepository<TransactionDetails, Long> {


    void deleteByTransactionId(Long transactionId);

    Optional<List<TransactionDetails>> findByTransactionId(Long transactionId);

    Page<TransactionDetails> findByTransactionId(Long transactionId, Pageable pageable);

    boolean existsByProduct_id(Long id);

    @Modifying
    @Query("DELETE FROM TransactionDetails td WHERE td.transaction.id = :transactionId AND td.product.id IN (SELECT p.id FROM Product p WHERE p.name = :name)")
    void removeProductFromTransaction(@Param("transactionId") Long transactionId, @Param("name") String name);


    @Query("SELECT new com.ang.rest.domain.dto.AnalyticsDto(s.name, TO_CHAR(t.date, 'YYYY-MM-DD'), SUM(td.price * td.quantity)) "
            + "FROM TransactionDetails td " + "INNER JOIN td.transaction t " + "INNER JOIN t.shop s " +
            "WHERE t.date >= :fromDate AND t.date <= :toDate " + "GROUP BY s.name, t.date")
    List<AnalyticsDto> getTotalSpent(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);


    @Query("SELECT new com.ang.rest.domain.dto.MonthCostDto(t.date, s.name, SUM(td.quantity * td.price)) " +
            "FROM TransactionDetails td " +
            "JOIN td.transaction t " +
            "JOIN td.product p " +
            "JOIN t.shop s " +
            "WHERE t.date BETWEEN :fromDate AND :toDate AND t.user.id = :userId " +
            "GROUP BY t.date, s.name")
    List<MonthCostDto> getMonthTotalWithShop(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, Long userId);



    @Query("SELECT new com.ang.rest.domain.dto.YearCostsDto(MONTH(t.date), COALESCE(SUM(td.price * td.quantity), 0)) " +
            "FROM TransactionDetails td " +
            "JOIN td.transaction t " +
            "WHERE YEAR(t.date) = :year AND t.user.id = :userId " +
            "GROUP BY MONTH(t.date)")
    List<YearCostsDto> getYearsTotals(@Param("year") int year, Long userId);




}




