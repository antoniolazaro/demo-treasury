package com.purshase.transaction.test.domain.model.entities;

import com.purshase.transaction.test.domain.model.enuns.Currency;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@lombok.Data
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purshase_transaction")
public class PurshaseTransaction {

    @Id
    @GeneratedValue
    private UUID id;
    @Size(min=2,max=50)
    @Column(nullable = false)
    private String description;
    @NotNull
    @Column(nullable = false)
    private LocalDate transactionDate;
    @Positive
    @NotNull
    @Column(nullable = false)
    private BigDecimal amount;
    @Column
    @NotNull
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @NotNull
    @Column(nullable = false)
    @Builder.Default
    private Currency currency = Currency.USD;
}
