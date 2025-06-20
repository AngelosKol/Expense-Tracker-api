package com.ang.rest.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;


public record AnalyticsDTO(
        String shopName,
        LocalDate transactionDate,
        BigDecimal totalSpent
) {
}
