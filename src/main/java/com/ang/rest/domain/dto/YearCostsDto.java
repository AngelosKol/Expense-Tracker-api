
package com.ang.rest.domain.dto;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;


@Getter
public class YearCostsDto {


    private String monthName;
    @Setter
    private BigDecimal totalSpent;

    public YearCostsDto(Integer month, BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
        this.monthName = getMonthName(month);
    }


    private String getMonthName(Integer month) {
        if(month == null || month < 1 || month > 12) {
            return "Invalid month";
        }
        return Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }




}
