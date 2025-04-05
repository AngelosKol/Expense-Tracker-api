package com.ang.rest.analytics;

import com.ang.rest.domain.dto.AnalyticsDTO;
import com.ang.rest.domain.dto.MonthCostDTO;
import com.ang.rest.domain.dto.YearCostsDTO;
import com.ang.rest.domain.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<TransactionDetails, Long> {
    @Query("SELECT new com.ang.rest.domain.dto.AnalyticsDTO(s.name, TO_CHAR(t.date, 'YYYY-MM-DD'), SUM(td.price * td.quantity)) "
            + "FROM TransactionDetails td " + "INNER JOIN td.transaction t " + "INNER JOIN t.shop s " +
            "WHERE t.date >= :fromDate AND t.date <= :toDate " + "GROUP BY s.name, t.date")
    List<AnalyticsDTO> getTotalSpent(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);


    @Query("SELECT new com.ang.rest.domain.dto.MonthCostDTO(t.date, s.name, SUM(td.quantity * td.price)) " +
            "FROM TransactionDetails td " +
            "JOIN td.transaction t " +
            "JOIN td.product p " +
            "JOIN t.shop s " +
            "WHERE t.date BETWEEN :fromDate AND :toDate AND t.user.id = :userId " +
            "GROUP BY t.date, s.name " +
            "ORDER BY t.date ")
    List<MonthCostDTO> getMonthTotalWithShop(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, Long userId);



    @Query("SELECT new com.ang.rest.domain.dto.YearCostsDTO(MONTH(t.date), COALESCE(SUM(td.price * td.quantity), 0)) " +
            "FROM TransactionDetails td " +
            "JOIN td.transaction t " +
            "WHERE YEAR(t.date) = :year AND t.user.id = :userId " +
            "GROUP BY MONTH(t.date) " +
            "ORDER BY MONTH(t.date) ")
    List<YearCostsDTO> getYearsTotals(@Param("year") int year, Long userId);



}
