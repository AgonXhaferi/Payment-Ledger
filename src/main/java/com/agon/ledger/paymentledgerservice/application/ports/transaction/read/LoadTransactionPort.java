package com.agon.ledger.paymentledgerservice.application.ports.transaction.read;

import com.agon.ledger.paymentledgerservice.domain.entity.Transaction;
import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface LoadTransactionPort {
    Optional<Transaction> loadTransaction(UUID transactionId);
    Page<Transaction> loadHistory(AccountId accountId, Pageable pageable);
}
