package com.figi.bks.controller;

import com.figi.bks.model.response.CalculationResponse;
import com.figi.bks.model.request.StockRequest;
import com.figi.bks.service.CalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class CalculationController {

    private final CalculationService calculationService;

    @PostMapping("/allocations")
    public ResponseEntity<CalculationResponse> calculatePortfolio(@RequestBody StockRequest request) {
        return ResponseEntity.ok(calculationService.calculatePortfolio(request.getStocks()));
    }
}
