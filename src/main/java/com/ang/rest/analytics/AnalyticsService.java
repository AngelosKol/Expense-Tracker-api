package com.ang.rest.analytics;

import com.ang.rest.auth.AuthenticatedUserUtil;
import com.ang.rest.domain.dto.AnalyticsDTO;
import com.ang.rest.domain.dto.MonthCostDTO;
import com.ang.rest.domain.dto.YearCostsDTO;
import com.ang.rest.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final AnalyticsRepository repository;
    private final AuthenticatedUserUtil authenticatedUserUtil;

    public List<AnalyticsDTO> getTotalSpentByDate(Date fromDate, Date toDate) {
        return repository.getTotalSpent(fromDate, toDate);
    }

    public List<YearCostsDTO> getYearTotals(int year) {
        User authenticatedUser = authenticatedUserUtil.getAuthenticatedUser();
        return repository.getYearsTotals(year, authenticatedUser.getId());
    }

    public List<MonthCostDTO> getMonthTotalsWithShop(int year, int month) {
        LocalDate fromDate = LocalDate.of(year, month, 1);
        LocalDate toDate = YearMonth.from(fromDate).atEndOfMonth();
        User authenticatedUser = authenticatedUserUtil.getAuthenticatedUser();
        return repository.getMonthTotalWithShop(fromDate, toDate, authenticatedUser.getId());
    }
}
