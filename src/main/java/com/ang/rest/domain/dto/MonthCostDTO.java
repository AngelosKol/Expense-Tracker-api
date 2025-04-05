package com.ang.rest.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MonthCostDTO(
        LocalDate  date,
         String shop,
         BigDecimal cost
) {
}
