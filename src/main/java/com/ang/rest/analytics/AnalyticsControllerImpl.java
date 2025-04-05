package com.ang.rest.analytics;


import com.ang.rest.domain.dto.AnalyticsDTO;
import com.ang.rest.domain.dto.MonthCostDTO;
import com.ang.rest.domain.dto.YearCostsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("api/v1/analytics")
public class AnalyticsControllerImpl  {
    private final AnalyticsService analyticsService;

    @GetMapping(path = "api/v1/analytics/{fromDate}/to/{toDate}")
    public List<AnalyticsDTO> getTotalSpentByDate(@PathVariable("fromDate") Date fromDate, @PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        return analyticsService.getTotalSpentByDate(fromDate, toDate);
    }

    @GetMapping(path = "api/v1/analytics/totalSpent/year/{year}")
    public List<YearCostsDTO> getTotalSpentByMonth(@PathVariable("year") int year) {
        return analyticsService.getYearTotals(year);
    }

    @GetMapping(path = "api/v1/analytics/totalSpent/year/{year}/month/{month}")
    public List<MonthCostDTO> getMonthTotalsWithShop(@PathVariable("year") int year, @PathVariable("month") int month) {
        return analyticsService.getMonthTotalsWithShop(year, month);

    }

}
