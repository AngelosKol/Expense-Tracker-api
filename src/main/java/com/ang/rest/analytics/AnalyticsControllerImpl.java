package com.ang.rest.analytics;


import com.ang.rest.domain.dto.AnalyticsDto;
import com.ang.rest.domain.dto.MonthCostDto;
import com.ang.rest.domain.dto.YearCostsDto;
import com.ang.rest.domain.entity.User;
import com.ang.rest.transaction_details.TransactionDetailsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
//@RequestMapping("api/v1/analytics")
public class AnalyticsControllerImpl  {
    private final TransactionDetailsService transactionDetailsService;

    public AnalyticsControllerImpl(TransactionDetailsService transactionDetailsService) {
        this.transactionDetailsService = transactionDetailsService;
    }


    @GetMapping(path = "api/v1/analytics/{fromDate}/to/{toDate}")
    public List<AnalyticsDto> getTotalSpentByDate(@PathVariable("fromDate") Date fromDate, @PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        return transactionDetailsService.getTotalSpentByDate(fromDate, toDate);
    }


    @GetMapping(path = "api/v1/analytics/totalSpent/year/{year}")
    public List<YearCostsDto> getTotalSpentByMonth(@PathVariable("year") int year) {
        return transactionDetailsService.getYearTotals(year);
    }



    @GetMapping(path = "api/v1/analytics/totalSpent/year/{year}/month/{month}")
    public List<MonthCostDto> getMonthTotalsWithShop(@PathVariable("year") int year, @PathVariable("month") int month) {
        return transactionDetailsService.getMonthTotalsWithShop(year, month);

    }


}
