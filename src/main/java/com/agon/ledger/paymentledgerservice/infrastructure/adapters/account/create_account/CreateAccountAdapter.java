package com.agon.ledger.paymentledgerservice.infrastructure.adapters.account.create_account;

import com.agon.ledger.paymentledgerservice.application.ports.account.write.SaveAccountPort;
import com.agon.ledger.paymentledgerservice.domain.entity.Account;
import com.agon.ledger.paymentledgerservice.infrastructure.persistence.repository.SpringDataAccountRepository;
import com.agon.ledger.paymentledgerservice.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAccountAdapter implements SaveAccountPort {
    private final SpringDataAccountRepository repository;
    private final AccountMapper mapper;


    @Override
    public void saveAccount(Account account) {
        var dbEntity = mapper.toPersistence(account);

        repository.save(dbEntity);
    }
}
