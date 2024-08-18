package com.ang.rest.analytics;


import com.ang.rest.domain.dto.AnalyticsDto;
import com.ang.rest.transaction_details.TransactionDetailsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/analytics")
public class AnalyticsControllerImpl  {
    private final TransactionDetailsService transactionDetailsService;

    public AnalyticsControllerImpl(TransactionDetailsService transactionDetailsService) {
        this.transactionDetailsService = transactionDetailsService;
    }


    @GetMapping(path = "/{fromDate}/to/{toDate}")
    public List<AnalyticsDto> getTotalSpentByDate(@PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate, @PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        return transactionDetailsService.getTotalSpentByDate(fromDate, toDate);
    }

    @GetMapping(path = "/totalSpent/year/{year}")
    public List<Object> getTotalSpentByMonth(@PathVariable("year") int year) {
        return transactionDetailsService.getYearTotals(year);

    }

    @GetMapping(path = "/totalSpent/year/{year}/month/{month}")
    public List<Object> getMonthTotalsWithShop(@PathVariable("year") String year, @PathVariable("month") String month) {
        return transactionDetailsService.getMonthTotalsWithShop(year, month);

    }


}
