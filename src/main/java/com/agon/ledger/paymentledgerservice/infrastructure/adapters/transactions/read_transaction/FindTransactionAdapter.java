package com.agon.ledger.paymentledgerservice.infrastructure.adapters.transactions.read_transaction;

import com.agon.ledger.paymentledgerservice.application.ports.transaction.read.LoadTransactionPort;
import com.agon.ledger.paymentledgerservice.domain.entity.Transaction;
import com.agon.ledger.paymentledgerservice.infrastructure.persistence.repository.SpringDataTransactionRepository;
import com.agon.ledger.paymentledgerservice.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindTransactionAdapter implements LoadTransactionPort {
    private final SpringDataTransactionRepository springDataTransactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public Optional<Transaction> loadTransaction(UUID transactionId) {
        var dbEntity = springDataTransactionRepository.findById(transactionId);

        return dbEntity.map(transactionMapper::toDomain);
    }
}
