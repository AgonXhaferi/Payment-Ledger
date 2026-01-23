package com.agon.ledger.paymentledgerservice.application.queries.transaction.handlers;

import com.agon.ledger.paymentledgerservice.application.ports.account.read.LoadAccountPort;
import com.agon.ledger.paymentledgerservice.application.ports.transaction.read.LoadTransactionPort;
import com.agon.ledger.paymentledgerservice.application.queries.transaction.GetTransactionHistoryQuery;
import com.agon.ledger.paymentledgerservice.domain.entity.Transaction;
import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;
import com.agon.ledger.paymentledgerservice.shared.domain_error.DomainError;
import libs.query.QueryHandler;
import libs.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTransactionHistoryQueryHandler implements QueryHandler<GetTransactionHistoryQuery, Result<Page<Transaction>, DomainError>> {
    private final LoadAccountPort loadAccountPort;
    private final LoadTransactionPort loadTransactionPort;

    @Override
    public Result<Page<Transaction>, DomainError> handle(GetTransactionHistoryQuery query) {
        var accountId = new AccountId(query.accountId());

        if (loadAccountPort.loadAccount(accountId).isEmpty()) {
            return Result.err(new DomainError.NotFound("Account not found", query.accountId().toString()));
        }

        var pageable = PageRequest.of(query.page(), query.size());

        Page<Transaction> history = loadTransactionPort.loadHistory(accountId, pageable);

        return Result.ok(history);
    }
}
