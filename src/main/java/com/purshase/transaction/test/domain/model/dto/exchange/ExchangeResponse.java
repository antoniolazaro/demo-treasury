package com.purshase.transaction.test.domain.model.dto.exchange;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@lombok.Data
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeResponse {

    private String id;
    private LocalDate transactionDate;
    private String description;
    private BigDecimal originalAmount;
    private BigDecimal exchangeRate;
    private BigDecimal convertedAmount;

}
