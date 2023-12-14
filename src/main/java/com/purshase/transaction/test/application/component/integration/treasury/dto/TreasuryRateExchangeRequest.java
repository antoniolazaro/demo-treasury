package com.purshase.transaction.test.application.component.integration.treasury.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreasuryRateExchangeRequest {

    private LocalDate startDate;
    private LocalDate endDate;
    private String countryCurrencyFilter;
    private String sort;
    private Integer pageNumber;
    private Integer pageSize;
}
