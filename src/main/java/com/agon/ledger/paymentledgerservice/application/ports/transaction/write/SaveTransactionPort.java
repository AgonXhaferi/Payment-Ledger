package com.agon.ledger.paymentledgerservice.application.ports.transaction.write;

import com.agon.ledger.paymentledgerservice.domain.entity.Transaction;

public interface SaveTransactionPort {
    void save(Transaction transaction);
}
