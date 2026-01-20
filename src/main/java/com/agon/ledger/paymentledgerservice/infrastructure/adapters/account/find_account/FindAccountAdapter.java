package com.agon.ledger.paymentledgerservice.infrastructure.adapters.account.find_account;

import com.agon.ledger.paymentledgerservice.application.ports.account.read.LoadAccountPort;
import com.agon.ledger.paymentledgerservice.domain.entity.Account;
import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;
import com.agon.ledger.paymentledgerservice.infrastructure.persistence.repository.SpringDataAccountRepository;
import com.agon.ledger.paymentledgerservice.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindAccountAdapter implements LoadAccountPort {
    private final SpringDataAccountRepository repository;
    private final AccountMapper mapper;

    @Override
    public Optional<Account> loadAccount(AccountId id) {
        var entity = repository.findById(id.value());

        return entity.map(mapper::toDomain);
    }
}
