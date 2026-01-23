package com.agon.ledger.paymentledgerservice.application.commands.account;

import com.agon.ledger.paymentledgerservice.shared.domain_error.DomainError;
import libs.command.Command;
import libs.result.Result;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAccountCommand(
        UUID accountId,
        BigDecimal initialBalance,
        String currency
) implements Command<Result<UUID, DomainError>> {
}