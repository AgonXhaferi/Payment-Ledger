package com.agon.ledger.paymentledgerservice.application.ports;

import com.agon.ledger.paymentledgerservice.domain.entity.AccountEntity;
import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;

import java.util.Optional;

public interface LoadAccountPort {
    Optional<AccountEntity> loadAccount(AccountId id);
}
