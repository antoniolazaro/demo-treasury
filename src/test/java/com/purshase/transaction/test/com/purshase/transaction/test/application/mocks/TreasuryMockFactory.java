package com.purshase.transaction.test.com.purshase.transaction.test.application.mocks;

import com.purshase.transaction.test.application.component.integration.treasury.dto.TreasuryRateExchangeDataItemResponse;
import com.purshase.transaction.test.application.component.integration.treasury.dto.TreasuryRateExchangeMetaResponse;
import com.purshase.transaction.test.application.component.integration.treasury.dto.TreasuryRateExchangeRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TreasuryMockFactory {

    public static String getSort() {
        var sort = "-country";
        return sort;
    }

    public static String getFilter() {
        var filter = "countryCurrencyDesc";
        return filter;
    }
    public static TreasuryRateExchangeMetaResponse getTreasuryRateExchangeMetaResponse() {
        return TreasuryRateExchangeMetaResponse.builder()
                .totalCount(1)
                .totalPage(1)
                .build();
    }
    public static TreasuryRateExchangeDataItemResponse getTreasuryRateExchangeDataItemResponse() {
        return TreasuryRateExchangeDataItemResponse.builder()
                .countryCurrencyDesc("EUA")
                .exchangeRate(BigDecimal.TEN)
                .recordDate(LocalDate.now())
                .country("country")
                .currency("currency")
                .build();
    }

    public static LocalDate getEndDate(LocalDate recordDate) {
        var recordDateEnd  = recordDate.minus(1, ChronoUnit.MONTHS);
        return recordDateEnd;
    }
    public static LocalDate getStartDate() {
        var recordDate = LocalDate.now();
        return recordDate;
    }
    public static TreasuryRateExchangeRequest getTreasuryRateExchangeRequest(
            String filter,
            LocalDate recordDate,
            LocalDate recordDateEnd,
            String sort
    ) {
        return TreasuryRateExchangeRequest
                .builder()
                .countryCurrencyFilter(filter)
                .startDate(recordDate)
                .endDate(recordDateEnd)
                .sort(sort)
                .pageNumber(1)
                .pageSize(5)
                .build();
    }
    public static String getFields() {
        var fields = "country_currency_desc,exchange_rate,record_date,country,currency";
        return fields;
    }
}
