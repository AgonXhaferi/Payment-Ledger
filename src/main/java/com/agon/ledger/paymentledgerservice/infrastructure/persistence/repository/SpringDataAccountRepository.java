package com.agon.ledger.paymentledgerservice.infrastructure.persistence.repository;

import com.agon.ledger.paymentledgerservice.infrastructure.persistence.entity.AccountPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataAccountRepository extends JpaRepository<AccountPersistenceEntity, UUID> {
}
