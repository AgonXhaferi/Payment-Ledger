package com.agon.ledger.paymentledgerservice.application.ports.transaction.read;

import com.agon.ledger.paymentledgerservice.domain.entity.Transaction;

import java.util.Optional;
import java.util.UUID;

public interface LoadTransactionPort {
    Optional<Transaction> loadTransaction(UUID transactionId);
}
