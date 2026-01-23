package com.agon.ledger.paymentledgerservice.infrastructure.adapters.transactions.create_transaction;

import com.agon.ledger.paymentledgerservice.application.ports.transaction.write.SaveTransactionPort;
import com.agon.ledger.paymentledgerservice.domain.entity.Transaction;
import com.agon.ledger.paymentledgerservice.infrastructure.persistence.repository.SpringDataTransactionRepository;
import com.agon.ledger.paymentledgerservice.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateTransactionAdapter implements SaveTransactionPort {
    private final SpringDataTransactionRepository springDataTransactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public void save(Transaction transaction) {
        var dbEntity = transactionMapper.toPersistence(transaction);

        springDataTransactionRepository.save(dbEntity);
    }
}
