package com.purshase.transaction.test.application.component.integration.treasury;

import com.purshase.transaction.test.application.component.integration.treasury.dto.TreasuryRateExchangeResponse;
import com.purshase.transaction.test.application.component.integration.feign.config.FeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "treasuryClient", url = "${integration.treasury.url}", configuration = FeignDefaultConfig.class)
public interface TreasuryClient {

    @GetMapping(path = "${integration.treasury.url.rates.exchanges}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    TreasuryRateExchangeResponse ratesOfExchanges(
            @RequestParam("fields") String fields,
            @RequestParam("filter") String filter,
            @RequestParam("sort") String sort,
            @RequestParam("page[number]") int pageNumber,
            @RequestParam("page[size]") int pageSize
    );
}