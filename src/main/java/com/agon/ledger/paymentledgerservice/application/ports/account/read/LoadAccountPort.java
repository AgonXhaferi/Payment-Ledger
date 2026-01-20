package com.agon.ledger.paymentledgerservice.application.ports.account.read;

import com.agon.ledger.paymentledgerservice.domain.entity.Account;
import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;

import java.util.Optional;

public interface LoadAccountPort {
    Optional<Account> loadAccount(AccountId id);
}
