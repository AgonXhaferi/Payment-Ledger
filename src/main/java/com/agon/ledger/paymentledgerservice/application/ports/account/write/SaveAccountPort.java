package com.agon.ledger.paymentledgerservice.application.ports.account.write;

import com.agon.ledger.paymentledgerservice.domain.entity.Account;

import java.util.UUID;

public interface SaveAccountPort {
    void saveAccount(Account account);
}
