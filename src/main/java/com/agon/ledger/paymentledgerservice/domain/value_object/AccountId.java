package com.agon.ledger.paymentledgerservice.domain.value_object;

import java.util.UUID;

public record AccountId(UUID value) {
    public AccountId {
        if (value == null) {
            throw new IllegalArgumentException("AccountId cannot be null");
        }
    }

    public static AccountId newId() {
        return new AccountId(UUID.randomUUID());
    }
}
