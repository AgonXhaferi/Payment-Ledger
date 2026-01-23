package com.agon.ledger.paymentledgerservice.application.commands.transaction.handlers;

import com.agon.ledger.paymentledgerservice.application.commands.transaction.TransferFundsCommand;
import com.agon.ledger.paymentledgerservice.application.ports.account.read.LoadAccountPort;
import com.agon.ledger.paymentledgerservice.application.ports.account.write.SaveAccountPort;
import com.agon.ledger.paymentledgerservice.application.ports.transaction.write.SaveTransactionPort;
import com.agon.ledger.paymentledgerservice.domain.entity.Transaction;
import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;
import com.agon.ledger.paymentledgerservice.shared.domain_error.DomainError;
import libs.command.CommandHandler;
import libs.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferFundsCommandHandler implements CommandHandler<
        TransferFundsCommand,
        Result<UUID, DomainError>
        > {
    private final LoadAccountPort loadAccountPort;
    private final SaveAccountPort saveAccountPort;
    private final SaveTransactionPort saveTransactionPort;

    @Override
    @Retryable(
            retryFor = {
                    OptimisticLockingFailureException.class,
                    DataIntegrityViolationException.class
            },
            maxAttempts = 10,
            backoff = @Backoff(delay = 100, multiplier = 2)
    )
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Result<UUID, DomainError> handle(TransferFundsCommand command) {

        System.out.println("RETRY DEBUG: Running on " + Thread.currentThread().getName() + " at " + System.currentTimeMillis());

        var sourceAccountOptional = loadAccountPort.loadAccount(new AccountId(command.sourceAccountId()));
        if (sourceAccountOptional.isEmpty()) {
            return Result.err(new DomainError.NotFound("Source account not found", command.sourceAccountId().toString()));
        }

        var sourceAccount = sourceAccountOptional.get();

        var targetAccountOptional = loadAccountPort.loadAccount(new AccountId(command.targetAccountId()));
        if (targetAccountOptional.isEmpty()) {
            return Result.err(new DomainError.NotFound("Target account not found", command.targetAccountId().toString()));
        }

        var targetAccount = targetAccountOptional.get();

        if (!sourceAccount.getCurrency().equals(command.currency()) ||
                !targetAccount.getCurrency().equals(command.currency())) {
            return Result.err(new DomainError.Conflict("Currency mismatch"));
        }

        var transactionResult = Transaction.create(
                sourceAccount.getId(),
                targetAccount.getId(),
                command.amount(),
                command.currency(),
                command.reference(),
                command.idempotencyKey()
        );

        if (transactionResult.isFailure()) {
            return Result.err(transactionResult.getError());
        }

        var transaction = transactionResult.unwrap();

        try {
            sourceAccount.withdraw(command.amount());
            targetAccount.deposit(command.amount());
            transaction.complete();
        } catch (IllegalStateException | IllegalArgumentException e) {
            transaction.fail(e.getMessage());
            saveTransactionPort.save(transaction);

            return Result.err(new DomainError.Conflict(e.getMessage()));
        }

        saveAccountPort.save(sourceAccount);
        saveAccountPort.save(targetAccount);
        saveTransactionPort.save(transaction);

        return Result.ok(transaction.getId());
    }
}
