package com.purshase.transaction.test.domain.model.dto.exchange;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRequest {

    private String id;
    private String exchangeTarget;

}
