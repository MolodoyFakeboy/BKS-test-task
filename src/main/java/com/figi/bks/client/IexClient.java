package com.figi.bks.client;

import com.figi.bks.client.model.CompanyInfo;
import com.figi.bks.client.model.HistoryPrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "Iex", url = "${api.iex.cloud.url}")
public interface IexClient {

    @GetMapping("/HISTORICAL_PRICES/{companyName}")
    List<HistoryPrice> getHistoryPriceByCompany(@PathVariable String companyName, @RequestParam String token);

    @GetMapping("/COMPANY_HISTORICAL/{companyName}")
    List<CompanyInfo> getCompanyInfoByName(@PathVariable String companyName, @RequestParam String token);

}
