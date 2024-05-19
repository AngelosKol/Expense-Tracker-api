package com.ang.rest.controllers;


import com.ang.rest.domain.dto.AnalyticsDto;
import com.ang.rest.repositories.TransactionDetailsRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class AnalyticsController {
    private TransactionDetailsRepository transactionDetailsRepository;
    public AnalyticsController(TransactionDetailsRepository transactionDetailsRepository){
        this.transactionDetailsRepository = transactionDetailsRepository;
    }



    @GetMapping(path = "/analytics/{fromDate}/{toDate}")
    public List<AnalyticsDto> getTotalSpentByDate(
            @PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        return transactionDetailsRepository.getTotalSpent(fromDate, toDate);
    }
}
