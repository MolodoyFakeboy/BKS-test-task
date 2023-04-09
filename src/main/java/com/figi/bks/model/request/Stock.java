package com.figi.bks.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {

    private String symbol;
    private int volume;
}
