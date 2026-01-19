package com.agon.ledger.paymentledgerservice.infrastructure.persistence_entities;

import com.agon.ledger.paymentledgerservice.infrastructure.base.AuditedPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions", schema = "ledger")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPersistenceEntity extends AuditedPersistenceEntity {
    @Column(name = "source_account_id", nullable = false)
    private String sourceAccountId;

    @Column(name = "target_account_id", nullable = false)
    private String targetAccountId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "reference")
    private String reference;

    @Column(name = "idempotency_key", nullable = false, unique = true)
    private String idempotencyKey;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "status", nullable = false)
    private String status;
}