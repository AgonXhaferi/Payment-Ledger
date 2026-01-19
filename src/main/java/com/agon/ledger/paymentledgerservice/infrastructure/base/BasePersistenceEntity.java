package com.agon.ledger.paymentledgerservice.infrastructure.base;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BasePersistenceEntity {
    @Id
    @Column(name = "id")
    private UUID id;
}
