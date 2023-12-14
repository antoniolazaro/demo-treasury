package com.purshase.transaction.test.application.component.integration.treasury.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreasuryRateExchangeResponse {

    private List<TreasuryRateExchangeDataItemResponse> data;
    private TreasuryRateExchangeMetaResponse meta;
}
