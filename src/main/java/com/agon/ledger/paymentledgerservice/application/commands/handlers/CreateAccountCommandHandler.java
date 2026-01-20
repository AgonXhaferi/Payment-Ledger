package com.agon.ledger.paymentledgerservice.application.commands.handlers;

import com.agon.ledger.paymentledgerservice.application.commands.CreateAccountCommand;
import com.agon.ledger.paymentledgerservice.application.ports.account.write.SaveAccountPort;
import com.agon.ledger.paymentledgerservice.domain.entity.Account;
import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;
import com.agon.ledger.paymentledgerservice.shared.domain_error.DomainError;
import libs.command.CommandHandler;
import libs.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateAccountCommandHandler implements CommandHandler<
        CreateAccountCommand,
        Result<UUID, DomainError>> {
    private final SaveAccountPort createAccountPort;

    @Override
    public Result<UUID, DomainError> handle(CreateAccountCommand command) {
        AccountId id = AccountId.newId();

        Account newAccount = Account.create(id, command.currency());

        try {
            createAccountPort.saveAccount(newAccount);

            return Result.ok(id.value());
        } catch (Exception ex) {
            return Result.err(new DomainError.Conflict("Could not save account"));
        }
    }
}
