package com.ang.rest.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@Data
@NoArgsConstructor
@Builder
public class AnalyticsDto {
    private String shopName;
    private String transactionDate;
    private BigDecimal totalSpent;


    public AnalyticsDto(String shopName, String transactionDate, BigDecimal totalSpent) {
        this.shopName = shopName;
        this.transactionDate = transactionDate;
        this.totalSpent = totalSpent;
    }
}
