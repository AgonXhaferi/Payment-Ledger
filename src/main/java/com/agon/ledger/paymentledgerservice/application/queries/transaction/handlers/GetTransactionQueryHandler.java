package com.agon.ledger.paymentledgerservice.application.queries.transaction.handlers;

import com.agon.ledger.paymentledgerservice.application.ports.transaction.read.LoadTransactionPort;
import com.agon.ledger.paymentledgerservice.application.queries.transaction.GetTransactionQuery;
import com.agon.ledger.paymentledgerservice.domain.entity.Transaction;
import com.agon.ledger.paymentledgerservice.shared.domain_error.DomainError;
import libs.query.QueryHandler;
import libs.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTransactionQueryHandler implements QueryHandler<GetTransactionQuery, Result<Transaction, DomainError>> {
    private final LoadTransactionPort loadTransactionPort;

    @Override
    public Result<Transaction, DomainError> handle(GetTransactionQuery query) {
        var transactionOptional = loadTransactionPort.loadTransaction(query.transactionId());

        if (transactionOptional.isEmpty()) {
            return Result.err(new DomainError.NotFound("Account not found", query.transactionId().toString()));
        }

        return Result.ok(transactionOptional.get());
    }
}
