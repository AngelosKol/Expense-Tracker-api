package com.ang.rest.analytics;

import com.ang.rest.BaseRepository;
import com.ang.rest.domain.dto.AnalyticsDTO;
import com.ang.rest.domain.dto.MonthCostDTO;
import com.ang.rest.domain.dto.YearCostsDTO;
import com.ang.rest.domain.entity.TransactionDetails;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class AnalyticsRepository extends BaseRepository<TransactionDetails, Long> {
    List<AnalyticsDTO> getTotalSpent( Date fromDate,  Date toDate) {
        String jpql = "SELECT new com.ang.rest.domain.dto.AnalyticsDTO(s.name, TO_CHAR(t.date, 'YYYY-MM-DD'), SUM(td.price * td.quantity)) "
                + "FROM TransactionDetails td " + "INNER JOIN td.transaction t " + "INNER JOIN t.shop s " +
                "WHERE t.date >= :fromDate AND t.date <= :toDate " + "GROUP BY s.name, t.date";
        return getEntityManager()
                .createQuery(jpql, AnalyticsDTO.class)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .getResultList();
    }
    List<MonthCostDTO> getMonthTotalWithShop(LocalDate fromDate,  LocalDate toDate, Long userId) {
        String jpql = "SELECT new com.ang.rest.domain.dto.MonthCostDTO(t.date, s.name, SUM(td.quantity * td.price)) " +
                "FROM TransactionDetails td " +
                "JOIN td.transaction t " +
                "JOIN td.product p " +
                "JOIN t.shop s " +
                "WHERE t.date BETWEEN :fromDate AND :toDate AND t.user.id = :userId " +
                "GROUP BY t.date, s.name " +
                "ORDER BY t.date ";
        return getEntityManager()
                .createQuery(jpql, MonthCostDTO.class)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setParameter("userId", userId)
                .getResultList();
    }

    List<YearCostsDTO> getYearsTotals(int year, Long userId) {
        String jpql =  "SELECT new com.ang.rest.domain.dto.YearCostsDTO(MONTH(t.date), COALESCE(SUM(td.price * td.quantity), 0)) " +
                "FROM TransactionDetails td " +
                "JOIN td.transaction t " +
                "WHERE YEAR(t.date) = :year AND t.user.id = :userId " +
                "GROUP BY MONTH(t.date) " +
                "ORDER BY MONTH(t.date) ";
        return getEntityManager()
                .createQuery(jpql, YearCostsDTO.class)
                .setParameter("year", year)
                .setParameter("userId", userId)
                .getResultList();
    }



}
