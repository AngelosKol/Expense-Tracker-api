package com.ang.rest.analytics;

import com.ang.rest.auth.AuthenticationService;
import com.ang.rest.domain.dto.AnalyticsDTO;
import com.ang.rest.domain.dto.MonthCostDTO;
import com.ang.rest.domain.dto.YearCostsDTO;
import com.ang.rest.domain.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class AnalyticsService {
    @Inject
    AnalyticsRepository repository;
    @Inject
    AuthenticationService authenticationService;

    public List<AnalyticsDTO> getTotalSpentByDate(Date fromDate, Date toDate) {
        return repository.getTotalSpent(fromDate, toDate);
    }

    public List<YearCostsDTO> getYearTotals(int year) {
        User authenticatedUser = authenticationService.getAuthenticatedUser();
        return repository.getYearsTotals(year, authenticatedUser.id);
    }

    public List<MonthCostDTO> getMonthTotalsWithShop(int year, int month) {
        LocalDate fromDate = LocalDate.of(year, month, 1);
        LocalDate toDate = YearMonth.from(fromDate).atEndOfMonth();
        User authenticatedUser = authenticationService.getAuthenticatedUser();
        return repository.getMonthTotalWithShop(fromDate, toDate, authenticatedUser.id);
    }
}
