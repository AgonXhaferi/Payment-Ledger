package com.agon.ledger.paymentledgerservice.domain.value_object;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.UUID;

public record AccountId(UUID value) {
    public AccountId {
        if (value == null) {
            throw new IllegalArgumentException("AccountId cannot be null");
        }
    }

    @JsonValue
    public UUID value() {
        return value;
    }

    public static AccountId newId() {
        return new AccountId(UUID.randomUUID());
    }
}
