package com.ang.rest.controllers;


import com.ang.rest.domain.dto.AnalyticsDto;
import com.ang.rest.domain.dto.YearlyCostDto;
import com.ang.rest.repositories.TransactionDetailsRepository;
import com.ang.rest.services.TransactionDetailsService;
import com.ang.rest.services.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class AnalyticsController {
    private TransactionDetailsService transactionDetailsService;
    public AnalyticsController(TransactionDetailsService transactionDetailsService){
        this.transactionDetailsService = transactionDetailsService;
    }



    @GetMapping(path = "/analytics/{fromDate}/to/{toDate}")
    public List<AnalyticsDto> getTotalSpentByDate(
            @PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        return transactionDetailsService.getTotalSpentByDate(fromDate, toDate);
    }

    @GetMapping(path = "/analytics/totalSpent/year/{year}")
    public List<Object> getTotalSpentByMonth(@PathVariable("year") int year){
        return transactionDetailsService.getYearTotals(year);

    }

    @GetMapping(path = "/analytics/totalSpent/year/{year}/month/{month}")
    public List<Object> getMonthTotals(@PathVariable("year") int year, @PathVariable("month")int month){
        return transactionDetailsService.getMonthTotals(year, month);

    }


}
