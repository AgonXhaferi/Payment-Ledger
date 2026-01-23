package com.agon.ledger.paymentledgerservice.application.queries.transaction;

import com.agon.ledger.paymentledgerservice.domain.entity.Transaction;
import com.agon.ledger.paymentledgerservice.shared.domain_error.DomainError;
import libs.query.Query;
import libs.result.Result;
import org.springframework.data.domain.Page;

import java.util.UUID;

public record GetTransactionHistoryQuery(
        UUID accountId,
        int page,
        int size
) implements Query<Result<Page<Transaction>, DomainError>> {
}