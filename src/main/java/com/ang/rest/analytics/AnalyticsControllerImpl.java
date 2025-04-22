package com.ang.rest.analytics;


import com.ang.rest.domain.dto.AnalyticsDTO;
import com.ang.rest.domain.dto.MonthCostDTO;
import com.ang.rest.domain.dto.YearCostsDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.*;


import java.util.Date;
import java.util.List;

@Path("api/v2/analytics")
public class AnalyticsControllerImpl  {

    @Inject
    AnalyticsService analyticsService;

    @GET
    @Path("/{fromDate}/to/{toDate}")
    public List<AnalyticsDTO> getTotalSpentByDate(@PathParam("fromDate") Date fromDate, @PathParam("toDate") Date toDate) {
        return analyticsService.getTotalSpentByDate(fromDate, toDate);
    }

    @GET
    @Path("/totalSpent/year/{year}")
    public List<YearCostsDTO> getTotalSpentByMonth(@PathParam("year") int year) {
        return analyticsService.getYearTotals(year);
    }

    @GET
    @Path("/totalSpent/year/{year}/month/{month}")
    public List<MonthCostDTO> getMonthTotalsWithShop(@PathParam("year") int year, @PathParam("month") int month) {
        return analyticsService.getMonthTotalsWithShop(year, month);

    }

}
