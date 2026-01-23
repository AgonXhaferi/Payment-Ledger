package com.agon.ledger.paymentledgerservice.application.commands.transaction;

import com.agon.ledger.paymentledgerservice.shared.domain_error.DomainError;
import libs.command.Command;
import libs.result.Result;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferFundsCommand(
        UUID sourceAccountId,
        UUID targetAccountId,
        BigDecimal amount,
        String currency,
        String reference,
        String idempotencyKey
) implements Command<Result<UUID, DomainError>> {
}