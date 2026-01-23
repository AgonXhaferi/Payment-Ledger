package com.agon.ledger.paymentledgerservice.infrastructure.persistence.repository;

import com.agon.ledger.paymentledgerservice.infrastructure.persistence.entity.TransactionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataTransactionRepository extends JpaRepository<TransactionPersistenceEntity, UUID> {
}
