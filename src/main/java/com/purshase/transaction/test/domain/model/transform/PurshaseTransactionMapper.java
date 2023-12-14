package com.purshase.transaction.test.domain.model.transform;

import com.purshase.transaction.test.domain.model.entities.PurshaseTransaction;
import com.purshase.transaction.test.domain.model.dto.purshase.PurshaseTransactionRequest;
import com.purshase.transaction.test.domain.model.dto.purshase.PurshaseTransactionResponse;
import org.mapstruct.*;

@Mapper(builder = @Builder(disableBuilder = true), nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PurshaseTransactionMapper {

    PurshaseTransaction toPurshaseTransaction(PurshaseTransactionRequest purshaseTransactionRequest);
    @Mapping(target = "id", ignore = true)
    PurshaseTransactionResponse toPurshaseTransactionResponse(PurshaseTransaction purshaseTransaction);

    @AfterMapping
    default void afterToBooking(@MappingTarget PurshaseTransactionResponse purshaseTransactionResponse, PurshaseTransaction purshaseTransaction) {
        purshaseTransactionResponse.setId(purshaseTransaction.getId().toString());
    }
}
