package com.figi.bks.client;

import com.figi.bks.error.exception.NotFoundException;
import com.figi.bks.model.CompanyInfoAmount;
import com.figi.bks.model.request.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IexService {

    private final IexClient iexClient;

    @Value("${api.iex.cloud.token}")
    private String token;

    @Async("taskExecutor")
    public CompletableFuture <Map<String, List<CompanyInfoAmount>>> findCompaniesInfo(List<Stock> stocks) {
        List<CompanyInfoAmount> companyInfos = new ArrayList<>();
        stocks.forEach(stock -> {
            var company = iexClient.getCompanyInfoByName(stock.getSymbol(), token).stream().findFirst()
                    .orElseThrow(() -> new NotFoundException("Couldn't find company by name: " + stock.getSymbol()));

            var infoWithAmount = CompanyInfoAmount.builder()
                    .amount(stock.getVolume())
                    .companyInfo(company)
                    .build();
            companyInfos.add(infoWithAmount);
        });
        var result = companyInfos.stream().collect(Collectors.groupingBy(companyInfoAmount -> companyInfoAmount.getCompanyInfo().getSector()));

        return CompletableFuture.completedFuture(result);
    }

    @Async("taskExecutor")
    public CompletableFuture <Map<String, BigDecimal>> findStocksPriceByName(List<Stock> stocks) {
        Map<String, BigDecimal> historyPrices = new HashMap<>();

        stocks.forEach(stock -> {
            var price = iexClient.getHistoryPriceByCompany(stock.getSymbol(), token).stream().findFirst()
                    .orElseThrow(() -> new NotFoundException("Couldn't find history price by name: " + stock.getSymbol()));

            historyPrices.put(price.getKey(), price.getClose());
        });

        return CompletableFuture.completedFuture(historyPrices);
    }
}
