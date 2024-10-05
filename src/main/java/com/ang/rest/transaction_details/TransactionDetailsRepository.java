package com.ang.rest.transaction_details;

import com.ang.rest.domain.dto.AnalyticsDto;
import com.ang.rest.domain.entity.TransactionDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long>, PagingAndSortingRepository<TransactionDetails, Long> {

    @Transactional
    void deleteByTransactionId(Long transactionId);

    Optional<List<TransactionDetails>> findByTransactionId(Long transactionId);

    Page<TransactionDetails> findByTransactionId(Long transactionId, Pageable pageable);

    boolean existsByProduct_id(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM TransactionDetails td WHERE td.transaction.id = :transactionId AND td.product.id IN (SELECT p.id FROM Product p WHERE p.name = :name)")
    void removeProductFromTransaction(@Param("transactionId") Long transactionId, @Param("name") String name);


    @Query("SELECT new com.ang.rest.domain.dto.AnalyticsDto(s.name, TO_CHAR(t.date, 'YYYY-MM-DD'), SUM(td.price * td.quantity)) " + "FROM TransactionDetails td " + "INNER JOIN td.transaction t " + "INNER JOIN t.shop s " + "WHERE t.date >= :fromDate AND t.date <= :toDate " + "GROUP BY s.name, t.date")
    List<AnalyticsDto> getTotalSpent(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);


    @Query(value = "SELECT day_and_month, shop_name, totalspent FROM get_daily_costs_with_shop(:yearParam, :monthParam)", nativeQuery = true)
    List<Object> getMonthTotalWithShop(@Param("yearParam") String yearParam, @Param("monthParam") String monthParam);


    @Query(value = "SELECT * from get_year_costs(:year)", nativeQuery = true)
    List<Object> getYearTotals(@Param("year") int year);


    @Query(value = "SELECT * from get_daily_costs(:year, :month)", nativeQuery = true)
    List<Object> getMonthTotals(@Param("year") String year, @Param("month") String month);


}




