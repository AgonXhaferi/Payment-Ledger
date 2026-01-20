package com.agon.ledger.paymentledgerservice._interface.http.user;

import com.agon.ledger.paymentledgerservice._interface.http.mapper.HttpExceptionMapper;
import com.agon.ledger.paymentledgerservice.application.queries.GetAccountQuery;
import com.agon.ledger.paymentledgerservice.domain.entity.AccountEntity;
import libs.query.QueryBus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountReadController {
    private final QueryBus queryBus;
    private final HttpExceptionMapper httpExceptionMapper;

    @GetMapping("/{id}")
    public ResponseEntity<AccountEntity> findAccount(@PathVariable UUID id) {
        var result = queryBus.execute(new GetAccountQuery(id));

        if (result.isFailure()) {
            throw httpExceptionMapper.map(result.getError());
        }

        return ResponseEntity.ok(result.unwrap());
    }
}
