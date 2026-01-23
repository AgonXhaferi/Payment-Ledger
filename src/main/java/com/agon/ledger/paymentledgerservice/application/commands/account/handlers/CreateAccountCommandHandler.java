package com.agon.ledger.paymentledgerservice.application.commands.account.handlers;

import com.agon.ledger.paymentledgerservice.application.commands.account.CreateAccountCommand;
import com.agon.ledger.paymentledgerservice.application.ports.account.write.SaveAccountPort;
import com.agon.ledger.paymentledgerservice.domain.entity.Account;
import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;
import com.agon.ledger.paymentledgerservice.shared.domain_error.DomainError;
import jakarta.transaction.Transactional;
import libs.command.CommandHandler;
import libs.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateAccountCommandHandler implements CommandHandler<
        CreateAccountCommand,
        Result<UUID, DomainError>> {
    private final SaveAccountPort saveAccountPort;

    @Override
    @Transactional
    public Result<UUID, DomainError> handle(CreateAccountCommand command) {
        AccountId id = (command.accountId() != null)
                ? new AccountId(command.accountId())
                : AccountId.newId();

        Account newAccount = Account.create(id, command.currency());

        if (command.initialBalance() != null && command.initialBalance().compareTo(BigDecimal.ZERO) > 0) {
            try {
                newAccount.deposit(command.initialBalance());
            } catch (IllegalArgumentException e) {
                return Result.err(new DomainError.BadRequest("Invalid initial balance: " + e.getMessage()));
            }
        }

        saveAccountPort.save(newAccount);

        return Result.ok(id.value());
    }
}
