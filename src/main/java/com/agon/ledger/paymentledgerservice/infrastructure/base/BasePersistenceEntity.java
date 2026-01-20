package com.agon.ledger.paymentledgerservice.infrastructure.base;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BasePersistenceEntity implements Persistable<UUID> {
    @Id
    @Column(name = "id")
    private UUID id;

    @Version
    @Column(name = "version")
    private Long version;

    @Override
    public boolean isNew() {
        return version == null;
    }
}