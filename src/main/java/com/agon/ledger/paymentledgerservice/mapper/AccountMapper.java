package com.agon.ledger.paymentledgerservice.mapper;

import com.agon.ledger.paymentledgerservice.domain.entity.AccountEntity;
import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;
import com.agon.ledger.paymentledgerservice.infrastructure.persistence.entity.AccountPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountEntity toDomain(AccountPersistenceEntity entity) {
        return new AccountEntity(
                new AccountId(entity.getId()),
                entity.getBalance(),
                entity.getCurrency(),
                entity.getVersion()
        );
    }

    public AccountPersistenceEntity toEntity(AccountEntity domain) {
        AccountPersistenceEntity entity = new AccountPersistenceEntity();
        //look into builder pattern.
        entity.setId(domain.getId().value());
        entity.setBalance(domain.getBalance());
        entity.setCurrency(domain.getCurrency());
        entity.setActive(true);
        entity.setVersion(domain.getVersion());

        return entity;
    }
}
