package com.purshase.transaction.test.application.service.impl;

import com.purshase.transaction.test.application.component.integration.treasury.dto.TreasuryRateExchangeRequest;
import com.purshase.transaction.test.application.component.treasury.TreasuryComponent;
import com.purshase.transaction.test.application.service.PurshaseTransactionService;
import com.purshase.transaction.test.domain.exceptions.notfound.ExchangeNotFoundException;
import com.purshase.transaction.test.domain.exceptions.notfound.PurshaseTransactionNotFoundException;
import com.purshase.transaction.test.domain.model.entities.PurshaseTransaction;
import com.purshase.transaction.test.domain.model.transform.PurshaseTransactionMapper;
import com.purshase.transaction.test.domain.repository.PurshaseTransactionRepository;
import com.purshase.transaction.test.domain.model.dto.exchange.ExchangeRequest;
import com.purshase.transaction.test.domain.model.dto.exchange.ExchangeResponse;
import com.purshase.transaction.test.domain.model.dto.purshase.PurshaseTransactionRequest;
import com.purshase.transaction.test.domain.model.dto.purshase.PurshaseTransactionResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@lombok.RequiredArgsConstructor
public class PurshaseTransactionServiceImpl implements PurshaseTransactionService {

    private final PurshaseTransactionRepository purshaseTransactionRepository;
    private final PurshaseTransactionMapper purshaseTransactionMapper;
    private final TreasuryComponent treasuryComponent;

    @Override
    @Transactional
    public PurshaseTransactionResponse write(PurshaseTransactionRequest request) {
        return purshaseTransactionMapper.toPurshaseTransactionResponse(
                purshaseTransactionRepository.save(
                        purshaseTransactionMapper.toPurshaseTransaction(request)
                )
        );
    }
    @Override
    public ExchangeResponse convertExchange(ExchangeRequest exchangeRequest) {
        var id = exchangeRequest.getId();
        var purshaseTransaction = purshaseTransactionRepository
                .findById(UUID.fromString(id))
                .orElseThrow(()-> new PurshaseTransactionNotFoundException(String.format("Invalid id %s", id)));

        BigDecimal exchangeRate = this.getExchangeRate(exchangeRequest, purshaseTransaction);

        return ExchangeResponse
                .builder()
                .id(purshaseTransaction.getId().toString())
                .description(purshaseTransaction.getDescription())
                .transactionDate(purshaseTransaction.getTransactionDate())
                .originalAmount(purshaseTransaction.getAmount())
                .convertedAmount(purshaseTransaction.getAmount().multiply(exchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP))
                .exchangeRate(exchangeRate)
                .build();
    }

    private BigDecimal getExchangeRate(ExchangeRequest exchangeRequest, PurshaseTransaction purshaseTransaction) {
        var rateExchange = treasuryComponent.findExchangeRate(TreasuryRateExchangeRequest.builder()
                .countryCurrencyFilter(exchangeRequest.getExchangeTarget())
                .startDate(purshaseTransaction.getTransactionDate().minus(6, ChronoUnit.MONTHS))
                .endDate(purshaseTransaction.getTransactionDate())
                .sort("-record_date")
                .pageNumber(1)
                .pageSize(10)
                .build());
        var bestExchangeRate = rateExchange.getData().stream().findFirst().orElseThrow(()->
                new ExchangeNotFoundException(String.format("There is exchange for this period %s",
                        purshaseTransaction.getTransactionDate().toString())));
        var exchangeRate = bestExchangeRate.getExchangeRate();
        return exchangeRate;
    }
}
