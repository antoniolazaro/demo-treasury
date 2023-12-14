package com.purshase.transaction.test.com.purshase.transaction.test.application.component.treasury.impl;

import com.purshase.transaction.test.application.component.integration.treasury.TreasuryClient;
import com.purshase.transaction.test.application.component.integration.treasury.dto.TreasuryRateExchangeResponse;
import com.purshase.transaction.test.application.component.treasury.impl.TreasuryComponentImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.purshase.transaction.test.com.purshase.transaction.test.application.mocks.TreasuryMockFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TreasuryComponentImplTest {

    @Mock
    private TreasuryClient treasuryClient;
    @InjectMocks
    private TreasuryComponentImpl treasuryComponent;
    private static final String DATE_FORMAT_INTEGRATION = "yyyy-MM-dd";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(treasuryComponent, "dateFormatIntegration", DATE_FORMAT_INTEGRATION);
    }
    @Test
    void findExchangeRateSuccess() {

        var recordDate = getStartDate();
        var recordDateEnd = getEndDate(recordDate);
        var filter = getFilter();
        var fields = getFields();
        var sort = getSort();

        var filterFormated = String.format("country_currency_desc:in:(%s),record_date:gte:%s,record_date:lte:%s",
                filter,
                recordDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT_INTEGRATION)),
                recordDateEnd.format(DateTimeFormatter.ofPattern(DATE_FORMAT_INTEGRATION)));
        when(treasuryClient.ratesOfExchanges(
                eq(fields),
                eq(filterFormated),
                eq(sort),
                eq(1),
                eq(5)
        )).thenReturn(TreasuryRateExchangeResponse.builder()
                .data(List.of(getTreasuryRateExchangeDataItemResponse()))
                .meta(getTreasuryRateExchangeMetaResponse())
                .build());

        var response = treasuryComponent.findExchangeRate(
                getTreasuryRateExchangeRequest(filter, recordDate, recordDateEnd, sort));

        assertNotNull(response, "Non Null response");
        assertNotNull(response.getData(), "Non Null response");
        assertNotNull(response.getMeta(), "Non Null response");
        assertEquals(1, response.getData().size(), "Non Null response");

        verify(treasuryClient, times(1))
                .ratesOfExchanges(eq(fields),
                        eq(filterFormated),
                        eq(sort),
                        eq(1),
                        eq(5));
    }
}
