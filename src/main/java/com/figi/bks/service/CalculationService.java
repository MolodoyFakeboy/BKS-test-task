package com.figi.bks.service;

import com.figi.bks.client.IexService;
import com.figi.bks.error.exception.CustomExecutionException;
import com.figi.bks.model.response.Allocation;
import com.figi.bks.model.response.CalculationResponse;
import com.figi.bks.model.CompanyInfoAmount;
import com.figi.bks.model.request.Stock;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;



@Service
@RequiredArgsConstructor
public class CalculationService {

    private final IexService iexService;

    public CalculationResponse calculatePortfolio(List<Stock> stocks)  {
        var companiesInfo = iexService.findCompaniesInfo(stocks);
        var stocksPriceByName = iexService.findStocksPriceByName(stocks);
        CompletableFuture.allOf(companiesInfo, stocksPriceByName).join();

        var sectorValues = calculateSectorValue(companiesInfo, stocksPriceByName);

        var portfolioValue = sectorValues.values()
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);


        var sectorAllocations = sectorValues.entrySet()
                .stream()
                .map(entry -> {
                    var sector = entry.getKey();
                    var value = entry.getValue();
                    var proportion = value.divide(portfolioValue, 3, RoundingMode.HALF_UP).doubleValue();
                    return new Allocation(sector, value, proportion);
                })
                .toList();

        return new CalculationResponse(sectorAllocations, portfolioValue);
    }

    private Map<String, BigDecimal> calculateSectorValue(
            CompletableFuture<Map<String, List<CompanyInfoAmount>>> companiesInfo,
            CompletableFuture<Map<String, BigDecimal>> stocksPriceByName) {
        var sectorValues = new HashMap<String, BigDecimal>();
        try {
            var prices = stocksPriceByName.get();
            companiesInfo.get().forEach((sector, companyInfoAmounts) -> companyInfoAmounts.forEach(companyWithAmount -> {
                var companyInfo = companyWithAmount.getCompanyInfo();
                var price = prices.get(companyInfo.getKey());
                var totalPrice = price.multiply(BigDecimal.valueOf(companyWithAmount.getAmount()))
                        .setScale(2, RoundingMode.HALF_UP);
                var sectorValue = sectorValues.getOrDefault(sector, BigDecimal.ZERO).add(totalPrice);
                sectorValues.put(sector, sectorValue);
            }));
        } catch (InterruptedException | ExecutionException e) {
            throw new CustomExecutionException("Error calculating portfolio: " + e.getMessage());
        }

        return sectorValues;
    }
}
