package com.agon.ledger.paymentledgerservice.application.queries.transaction;

import com.agon.ledger.paymentledgerservice.domain.entity.Transaction;
import com.agon.ledger.paymentledgerservice.shared.domain_error.DomainError;
import libs.query.Query;
import libs.result.Result;

import java.util.UUID;

public record GetTransactionQuery(UUID transactionId) implements Query<Result<Transaction, DomainError>> {
}
