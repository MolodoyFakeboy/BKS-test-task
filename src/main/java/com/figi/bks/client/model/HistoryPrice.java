package com.figi.bks.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryPrice {

    private BigDecimal close;
    private String key;
}
