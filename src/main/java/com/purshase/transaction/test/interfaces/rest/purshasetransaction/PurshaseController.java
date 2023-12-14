package com.purshase.transaction.test.interfaces.rest.purshasetransaction;

import com.purshase.transaction.test.application.service.PurshaseTransactionService;
import com.purshase.transaction.test.domain.model.dto.exchange.ExchangeRequest;
import com.purshase.transaction.test.domain.model.dto.exchange.ExchangeResponse;
import com.purshase.transaction.test.domain.model.dto.purshase.PurshaseTransactionRequest;
import com.purshase.transaction.test.domain.model.dto.purshase.PurshaseTransactionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/purshase")
@RestController
@RequiredArgsConstructor
public class PurshaseController {

    private final PurshaseTransactionService purshaseTransactionService;

    @PostMapping
    public ResponseEntity<PurshaseTransactionResponse> purshase(@RequestBody @Valid PurshaseTransactionRequest request) {
        return ResponseEntity.ok(purshaseTransactionService.write(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExchangeResponse> convertExchange(@PathVariable(name = "id") String id,
                                                            @RequestParam(name = "targetExchange") String targetExchange) {
        return ResponseEntity.ok(purshaseTransactionService.convertExchange(ExchangeRequest.builder().id(id).exchangeTarget(targetExchange).build()));
    }
}
