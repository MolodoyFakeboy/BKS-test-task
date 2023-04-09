package com.figi.bks.model;

import com.figi.bks.client.model.CompanyInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyInfoAmount {
    private CompanyInfo companyInfo;
    private int amount;
}
