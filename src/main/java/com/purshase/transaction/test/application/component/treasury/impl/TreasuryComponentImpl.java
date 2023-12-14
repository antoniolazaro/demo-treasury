package com.purshase.transaction.test.application.component.treasury.impl;

import com.purshase.transaction.test.application.component.integration.treasury.TreasuryClient;
import com.purshase.transaction.test.application.component.integration.treasury.dto.TreasuryRateExchangeRequest;
import com.purshase.transaction.test.application.component.integration.treasury.dto.TreasuryRateExchangeResponse;
import com.purshase.transaction.test.application.component.treasury.TreasuryComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class TreasuryComponentImpl implements TreasuryComponent {

    private final TreasuryClient treasuryClient;
    @Value("${integration.treasury.date-format}")
    private String dateFormatIntegration;

    public TreasuryRateExchangeResponse findExchangeRate(TreasuryRateExchangeRequest treasuryRateExchangeRequest) {

        var dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormatIntegration);

        var filterParam = String.format("country_currency_desc:in:(%s),record_date:gte:%s,record_date:lte:%s",
                treasuryRateExchangeRequest.getCountryCurrencyFilter(),
                treasuryRateExchangeRequest.getStartDate().format(dateTimeFormatter),
                treasuryRateExchangeRequest.getEndDate().format(dateTimeFormatter));

        var pageNumber = Objects.nonNull(treasuryRateExchangeRequest.getPageNumber())?treasuryRateExchangeRequest.getPageNumber():1;
        var pageSize = Objects.nonNull(treasuryRateExchangeRequest.getPageSize())?treasuryRateExchangeRequest.getPageSize():10;

        return treasuryClient.ratesOfExchanges(
                "country_currency_desc,exchange_rate,record_date,country,currency",
                filterParam,
                treasuryRateExchangeRequest.getSort(),
                pageNumber,
                pageSize
        );
    }
}
