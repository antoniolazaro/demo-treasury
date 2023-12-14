package com.purshase.transaction.test.com.purshase.transaction.test.application.service.impl;

import com.purshase.transaction.test.application.component.integration.treasury.dto.TreasuryRateExchangeDataItemResponse;
import com.purshase.transaction.test.application.component.integration.treasury.dto.TreasuryRateExchangeMetaResponse;
import com.purshase.transaction.test.application.component.integration.treasury.dto.TreasuryRateExchangeRequest;
import com.purshase.transaction.test.application.component.integration.treasury.dto.TreasuryRateExchangeResponse;
import com.purshase.transaction.test.application.component.treasury.TreasuryComponent;
import com.purshase.transaction.test.application.service.impl.PurshaseTransactionServiceImpl;
import com.purshase.transaction.test.domain.exceptions.notfound.ExchangeNotFoundException;
import com.purshase.transaction.test.domain.exceptions.notfound.PurshaseTransactionNotFoundException;
import com.purshase.transaction.test.domain.model.dto.exchange.ExchangeRequest;
import com.purshase.transaction.test.domain.model.dto.purshase.PurshaseTransactionRequest;
import com.purshase.transaction.test.domain.model.dto.purshase.PurshaseTransactionResponse;
import com.purshase.transaction.test.domain.model.entities.PurshaseTransaction;
import com.purshase.transaction.test.domain.model.transform.PurshaseTransactionMapper;
import com.purshase.transaction.test.domain.repository.PurshaseTransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PurshaseTransactionServiceImplTest {

    @Mock
    private PurshaseTransactionRepository purshaseTransactionRepository;
    @Mock
    private PurshaseTransactionMapper purshaseTransactionMapper;
    @Mock
    private TreasuryComponent treasuryComponent;
    @InjectMocks
    private PurshaseTransactionServiceImpl purshaseTransactionService;

    @Test
    void writeSuccess() {

        var request = PurshaseTransactionRequest.builder()
                .transactionDate(LocalDate.now())
                .description("test")
                .amount(BigDecimal.TEN)
                .build();

        var entity = PurshaseTransaction.builder()
                .transactionDate(LocalDate.now())
                .description("test")
                .amount(BigDecimal.TEN)
                .build();
        when(purshaseTransactionMapper.toPurshaseTransaction(
                eq(request)
        )).thenReturn(entity);

        var uuid = UUID.randomUUID();
        entity.setId(uuid);
        when(purshaseTransactionRepository.save(
                eq(entity)
        )).thenReturn(entity);

        var purshaseTransactionResponse = PurshaseTransactionResponse
                .builder()
                .id(uuid.toString())
                .transactionDate(request.getTransactionDate())
                .description(request.getDescription())
                .amount(request.getAmount())
                .build();

        when(purshaseTransactionMapper.toPurshaseTransactionResponse(
                eq(entity)
        )).thenReturn(purshaseTransactionResponse);

        var response = purshaseTransactionService.write(request);

        assertNotNull(response, "Non Null response");
        assertEquals(uuid.toString(), response.getId(), "Non Null response");
        assertEquals(request.getTransactionDate(), response.getTransactionDate(), "Non Null response");
        assertEquals(request.getDescription(), response.getDescription(), "Non Null response");
        assertEquals(request.getAmount(), response.getAmount(), "Non Null response");

        verify(purshaseTransactionRepository, times(1)).save(eq(entity));
        verify(purshaseTransactionMapper, times(1)).toPurshaseTransaction(eq(request));
        verify(purshaseTransactionMapper, times(1)).toPurshaseTransactionResponse(eq(entity));
    }

    @Test
    void convertExchangeSuccess() {

        var uuid = UUID.randomUUID();
        var request = ExchangeRequest.builder()
                .id(uuid.toString())
                .exchangeTarget("Canada-Dollar")
                .build();

        var currentDate = LocalDate.now();

        var entity = PurshaseTransaction.builder()
                .transactionDate(currentDate)
                .description("test")
                .amount(BigDecimal.TEN)
                .build();

        entity.setId(uuid);
        when(purshaseTransactionRepository.findById(
                eq(uuid)
        )).thenReturn(Optional.of(entity));

        var treasuryRateExchangeRequest = TreasuryRateExchangeRequest.builder()
                .countryCurrencyFilter(request.getExchangeTarget())
                .startDate(currentDate.minus(6, ChronoUnit.MONTHS))
                .endDate(currentDate)
                .sort("-record_date")
                .pageNumber(1)
                .pageSize(10)
                .build();

        var exchangeRate = BigDecimal.valueOf(12.345);
        var purshaseTransactionResponse = TreasuryRateExchangeResponse
                .builder()
                .data(List.of(TreasuryRateExchangeDataItemResponse.builder()
                        .countryCurrencyDesc("EUA")
                        .exchangeRate(exchangeRate)
                        .recordDate(LocalDate.now())
                        .country("country")
                        .currency("currency")
                        .build()))
                .meta(TreasuryRateExchangeMetaResponse.builder()
                        .totalCount(1)
                        .totalPage(1)
                        .build())
                .build();

        when(treasuryComponent.findExchangeRate(
                eq(treasuryRateExchangeRequest)
        )).thenReturn(purshaseTransactionResponse);

        var response = purshaseTransactionService.convertExchange(request);

        assertNotNull(response, "Non Null response");
        assertEquals(entity.getId().toString(), response.getId(), "Non Null response");
        assertEquals(entity.getTransactionDate(), response.getTransactionDate(), "Non Null response");
        assertEquals(entity.getDescription(), response.getDescription(), "Non Null response");
        assertEquals(entity.getAmount(), response.getOriginalAmount(), "Non Null response");
        assertEquals(exchangeRate, response.getExchangeRate(), "Non Null response");
        var convertedAmount = entity.getAmount().multiply(exchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertEquals(convertedAmount, response.getConvertedAmount(), "Non Null response");

        verify(purshaseTransactionRepository, times(1)).findById(eq(uuid));
        verify(treasuryComponent, times(1)).findExchangeRate(eq(treasuryRateExchangeRequest));
    }

    @Test
    void convertExchangeInvalidId() {

        var uuid = UUID.randomUUID();
        var request = ExchangeRequest.builder()
                .id(uuid.toString())
                .exchangeTarget("Canada-Dollar")
                .build();

        when(purshaseTransactionRepository.findById(
                eq(uuid)
        )).thenReturn(Optional.empty());

        assertThrows(PurshaseTransactionNotFoundException.class, () ->
                        purshaseTransactionService.convertExchange(request),
                "Should be thrown BookingNotFoundException");

        verify(purshaseTransactionRepository, times(1)).findById(eq(uuid));
        verify(treasuryComponent, times(0)).findExchangeRate(any());
    }

    @Test
    void convertExchangeExchangeRateInvalid() {

        var uuid = UUID.randomUUID();
        var request = ExchangeRequest.builder()
                .id(uuid.toString())
                .exchangeTarget("Canada-Dollar")
                .build();

        var currentDate = LocalDate.now();

        var entity = PurshaseTransaction.builder()
                .transactionDate(currentDate)
                .description("test")
                .amount(BigDecimal.TEN)
                .build();

        entity.setId(uuid);
        when(purshaseTransactionRepository.findById(
                eq(uuid)
        )).thenReturn(Optional.of(entity));

        var treasuryRateExchangeRequest = TreasuryRateExchangeRequest.builder()
                .countryCurrencyFilter(request.getExchangeTarget())
                .startDate(currentDate.minus(6, ChronoUnit.MONTHS))
                .endDate(currentDate)
                .sort("-record_date")
                .pageNumber(1)
                .pageSize(10)
                .build();

        var purshaseTransactionResponse = TreasuryRateExchangeResponse
                .builder()
                .data(List.of())
                .meta(TreasuryRateExchangeMetaResponse.builder()
                        .totalCount(0)
                        .totalPage(0)
                        .build())
                .build();

        when(treasuryComponent.findExchangeRate(
                eq(treasuryRateExchangeRequest)
        )).thenReturn(purshaseTransactionResponse);

        assertThrows(ExchangeNotFoundException.class, () ->
                        purshaseTransactionService.convertExchange(request),
                "Should be thrown BookingNotFoundException");

        verify(purshaseTransactionRepository, times(1)).findById(eq(uuid));
        verify(treasuryComponent, times(1)).findExchangeRate(eq(treasuryRateExchangeRequest));
    }
}
