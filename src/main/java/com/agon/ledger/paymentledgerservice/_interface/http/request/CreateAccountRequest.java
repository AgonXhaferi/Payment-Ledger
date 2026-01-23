package com.agon.ledger.paymentledgerservice._interface.http.request;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAccountRequest(String currency, UUID accountId, BigDecimal amount) {
}