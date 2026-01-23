package com.agon.ledger.paymentledgerservice.infrastructure.adapters.transactions.read_transaction;

import com.agon.ledger.paymentledgerservice.application.ports.transaction.read.LoadTransactionPort;
import com.agon.ledger.paymentledgerservice.domain.entity.Transaction;
import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;
import com.agon.ledger.paymentledgerservice.infrastructure.persistence.repository.SpringDataTransactionRepository;
import com.agon.ledger.paymentledgerservice.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindTransactionAdapter implements LoadTransactionPort {
    private final SpringDataTransactionRepository repository;
    private final TransactionMapper mapper;

    @Override
    public Optional<Transaction> loadTransaction(UUID transactionId) {
        var dbEntity = repository.findById(transactionId);

        return dbEntity.map(mapper::toDomain);
    }

    @Override
    public Page<Transaction> loadHistory(AccountId accountId, Pageable pageable) {
        var entityPage = repository.findAllByAccountId(accountId.value(), pageable);

        return entityPage.map(mapper::toDomain);
    }
}
