package com.agon.ledger.paymentledgerservice.mapper;

import com.agon.ledger.paymentledgerservice.domain.entity.Transaction;
import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;
import com.agon.ledger.paymentledgerservice.domain.value_object.TransactionStatus;
import com.agon.ledger.paymentledgerservice.infrastructure.persistence.entity.TransactionPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public Transaction toDomain(TransactionPersistenceEntity entity) {
        return Transaction.restore(
                entity.getId(),
                new AccountId(entity.getSourceAccountId()),
                new AccountId(entity.getTargetAccountId()),
                entity.getAmount(),
                entity.getCurrency(),
                entity.getReference(),
                entity.getIdempotencyKey(),
                entity.getCreatedAt(),
                TransactionStatus.valueOf(entity.getStatus()),
                entity.getVersion(),
                null
        );
    }

    public TransactionPersistenceEntity toPersistence(Transaction domain) {
        var entity = new TransactionPersistenceEntity();

        entity.setId(domain.getId());
        entity.setVersion(domain.getVersion());

        entity.setSourceAccountId(domain.getSourceAccountId().value());
        entity.setTargetAccountId(domain.getTargetAccountId().value());
        entity.setAmount(domain.getAmount());
        entity.setCurrency(domain.getCurrency());
        entity.setReference(domain.getReference());
        entity.setIdempotencyKey(domain.getIdempotencyKey());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setStatus(domain.getStatus().name());

        return entity;
    }
}
