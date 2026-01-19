package com.agon.ledger.paymentledgerservice.infrastructure.base;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.util.UUID;
@MappedSuperclass
@Getter
public abstract class BasePersistenceEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }
}
