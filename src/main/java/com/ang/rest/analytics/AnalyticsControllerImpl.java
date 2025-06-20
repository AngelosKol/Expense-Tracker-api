package com.ang.rest.analytics;


import com.ang.rest.domain.dto.AnalyticsDTO;
import com.ang.rest.domain.dto.MonthCostDTO;
import com.ang.rest.domain.dto.YearCostsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/analytics")
public class AnalyticsControllerImpl  {
    private final AnalyticsService analyticsService;

    @GetMapping(path = "{fromDate}/to/{toDate}")
    public List<AnalyticsDTO> getTotalSpentByDate(@PathVariable Date fromDate, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        return analyticsService.getTotalSpentByDate(fromDate, toDate);
    }

    @GetMapping(path = "totalSpent/year/{year}")
    public List<YearCostsDTO> getTotalSpentByMonth(@PathVariable int year) {
        return analyticsService.getYearTotals(year);
    }

    @GetMapping(path = "totalSpent/year/{year}/month/{month}")
    public List<MonthCostDTO> getMonthTotalsWithShop(@PathVariable int year, @PathVariable int month) {
        return analyticsService.getMonthTotalsWithShop(year, month);

    }

}
