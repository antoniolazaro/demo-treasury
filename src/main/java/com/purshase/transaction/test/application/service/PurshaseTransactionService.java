package com.purshase.transaction.test.application.service;

import com.purshase.transaction.test.domain.model.dto.exchange.ExchangeRequest;
import com.purshase.transaction.test.domain.model.dto.exchange.ExchangeResponse;
import com.purshase.transaction.test.domain.model.dto.purshase.PurshaseTransactionRequest;
import com.purshase.transaction.test.domain.model.dto.purshase.PurshaseTransactionResponse;

public interface PurshaseTransactionService {
    PurshaseTransactionResponse write(PurshaseTransactionRequest request);
    ExchangeResponse convertExchange(ExchangeRequest exchangeRequest);

}
