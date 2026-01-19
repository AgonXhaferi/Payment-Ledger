package com.agon.ledger.paymentledgerservice.infrastructure.persistence_entities;

import com.agon.ledger.paymentledgerservice.infrastructure.base.AuditedPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts", schema = "ledger")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountPersistenceEntity extends AuditedPersistenceEntity {
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Version
    @Column(name = "version")
    private Long version;
}
