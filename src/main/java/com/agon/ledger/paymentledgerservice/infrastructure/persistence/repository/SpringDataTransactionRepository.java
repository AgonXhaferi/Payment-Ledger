package com.agon.ledger.paymentledgerservice.infrastructure.persistence.repository;

import com.agon.ledger.paymentledgerservice.infrastructure.persistence.entity.TransactionPersistenceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataTransactionRepository extends JpaRepository<TransactionPersistenceEntity, UUID> {
    @Query("SELECT t FROM TransactionPersistenceEntity t " +
            "WHERE t.sourceAccountId = :accountId OR t.targetAccountId = :accountId " +
            "ORDER BY t.createdAt DESC")
    Page<TransactionPersistenceEntity> findAllByAccountId(
            @Param("accountId") UUID accountId,
            Pageable pageable
    );
}
