package com.agon.ledger.paymentledgerservice.application.queries.handlers;

import com.agon.ledger.paymentledgerservice.application.ports.account.read.LoadAccountPort;
import com.agon.ledger.paymentledgerservice.application.queries.GetAccountQuery;
import com.agon.ledger.paymentledgerservice.domain.entity.Account;
import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;
import com.agon.ledger.paymentledgerservice.shared.domain_error.DomainError;
import libs.query.QueryHandler;
import libs.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAccountQueryHandler implements QueryHandler<GetAccountQuery, Result<Account, DomainError>> {
    private final LoadAccountPort loadAccountPort;

    @Override
    public Result<Account, DomainError> handle(GetAccountQuery query) {
        var accountOptional = loadAccountPort.loadAccount(
                new AccountId(query.accountId())
        );

        return accountOptional
                .<Result<Account, DomainError>>map(Result::ok)
                .orElseGet(() -> Result.err(
                        new DomainError.NotFound(
                                "Account not found",
                                query.accountId().toString()
                        )
                ));
    }
}
