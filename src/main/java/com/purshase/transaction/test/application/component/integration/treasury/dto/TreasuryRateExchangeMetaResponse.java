package com.purshase.transaction.test.application.component.integration.treasury.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreasuryRateExchangeMetaResponse {

    @JsonProperty("total_count")
    private int totalCount;
    @JsonProperty("total_page")
    private int totalPage;
}
