package com.agon.ledger.paymentledgerservice._interface.http.request;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferFundsRequest(
        UUID sourceAccountId,
        UUID targetAccountId,
        BigDecimal amount,
        String currency,
        String reference,
        String idempotencyKey
) {
}