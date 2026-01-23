package com.agon.ledger.paymentledgerservice.application.ports.account.write;

import com.agon.ledger.paymentledgerservice.domain.entity.Account;

public interface SaveAccountPort {
    void save(Account account);
}
