package com.purshase.transaction.test.domain.model.dto.purshase;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@lombok.Data
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurshaseTransactionRequest {

    @Size(min=2,max=50)
    @NotBlank
    private String description;
    @NotNull
    private LocalDate transactionDate;
    @Positive
    @NotNull
    private BigDecimal amount;

}
