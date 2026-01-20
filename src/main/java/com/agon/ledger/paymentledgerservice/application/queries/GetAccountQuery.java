package com.agon.ledger.paymentledgerservice.application.queries;

import com.agon.ledger.paymentledgerservice.domain.entity.Account;
import com.agon.ledger.paymentledgerservice.shared.domain_error.DomainError;
import libs.query.Query;
import libs.result.Result;

import java.util.UUID;

public record GetAccountQuery(UUID accountId) implements Query<Result<Account, DomainError>> {
}
