package com.agon.ledger.paymentledgerservice.application.queries.query_handlers;

import com.agon.ledger.paymentledgerservice.application.ports.account.read.LoadAccountPort;
import com.agon.ledger.paymentledgerservice.application.queries.GetAccountQuery;
import com.agon.ledger.paymentledgerservice.domain.entity.AccountEntity;
import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;
import com.agon.ledger.paymentledgerservice.shared.domain_error.DomainError;
import libs.query.QueryHandler;
import libs.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAccountQueryHandler implements QueryHandler<GetAccountQuery, Result<AccountEntity, DomainError>> {
    private final LoadAccountPort loadAccountPort;

    @Override
    public Result<AccountEntity, DomainError> handle(GetAccountQuery query) {
        var accountOptional = loadAccountPort.loadAccount(
                new AccountId(query.accountId())
        );

        return accountOptional
                .<Result<AccountEntity, DomainError>>map(Result::ok)
                .orElseGet(() -> Result.err(
                        new DomainError.NotFound(
                                "Account not found",
                                query.accountId().toString()
                        )
                ));
    }
}
