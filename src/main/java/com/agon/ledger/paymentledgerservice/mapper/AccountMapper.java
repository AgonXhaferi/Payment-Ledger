package com.agon.ledger.paymentledgerservice.mapper;

import com.agon.ledger.paymentledgerservice.domain.entity.Account;
import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;
import com.agon.ledger.paymentledgerservice.infrastructure.persistence.entity.AccountPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account toDomain(AccountPersistenceEntity entity) {
        return new Account(
                new AccountId(entity.getId()),
                entity.getBalance(),
                entity.getCurrency(),
                entity.getVersion()
        );
    }

    public AccountPersistenceEntity toPersistence(Account domain) {
        var entity = new AccountPersistenceEntity();

        entity.setId(domain.getId().value());
        entity.setBalance(domain.getBalance());
        entity.setCurrency(domain.getCurrency());
        entity.setActive(true);

        if (domain.getVersion() == null) {
            entity.setVersion(null);
        } else {
            entity.setVersion(domain.getVersion());
        }

        return entity;
    }
}
