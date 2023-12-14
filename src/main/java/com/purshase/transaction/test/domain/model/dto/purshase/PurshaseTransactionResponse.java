package com.purshase.transaction.test.domain.model.dto.purshase;

import com.purshase.transaction.test.domain.model.enuns.Currency;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@lombok.Data
@lombok.Builder
@AllArgsConstructor
public class PurshaseTransactionResponse {

    private String id;
    private String description;
    private LocalDate transactionDate;
    private LocalDateTime createdAt;
    private BigDecimal amount;
    private Currency currency;
}
