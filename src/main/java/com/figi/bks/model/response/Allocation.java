package com.figi.bks.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Allocation {

    private String sector;
    private BigDecimal assetValue;
    private double proportion;
}
