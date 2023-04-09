package com.figi.bks.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class CalculationResponse {

    private List<Allocation> allocations;
    private BigDecimal value;
}
