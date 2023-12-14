package com.purshase.transaction.test.application.component.integration.treasury.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreasuryRateExchangeDataItemResponse {

    @JsonProperty("record_date")
    private LocalDate recordDate;
    private String country;
    private String currency;
    @JsonProperty("country_currency_desc")
    private String countryCurrencyDesc;
    @JsonProperty("exchange_rate")
    private BigDecimal exchangeRate;
}
