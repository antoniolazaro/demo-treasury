package com.purshase.transaction.test.application.component.treasury;

import com.purshase.transaction.test.application.component.integration.treasury.dto.TreasuryRateExchangeRequest;
import com.purshase.transaction.test.application.component.integration.treasury.dto.TreasuryRateExchangeResponse;

public interface TreasuryComponent {

    TreasuryRateExchangeResponse findExchangeRate(TreasuryRateExchangeRequest treasuryRateExchangeRequest);
}
