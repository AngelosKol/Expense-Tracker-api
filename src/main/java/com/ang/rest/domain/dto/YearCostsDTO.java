
package com.ang.rest.domain.dto;
import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;


public record YearCostsDTO(
        String monthName,
        BigDecimal totalSpent
) {
    public YearCostsDTO(Integer month, BigDecimal totalSpent) {
      this(getMonthName(month), totalSpent);
    }

    private static String getMonthName(Integer month) {
        if(month == null || month < 1 || month > 12) {
            return "Invalid month";
        }
        return Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }




}
