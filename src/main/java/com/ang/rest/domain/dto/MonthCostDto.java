package com.ang.rest.domain.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MonthCostDto {
    LocalDate  date;
    private String shop;
    private BigDecimal cost;

    public MonthCostDto(LocalDate date, String shop, BigDecimal cost) {
        this.date = date;
        this.shop = shop;
        this.cost = cost;
    }

}
