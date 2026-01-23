package com.agon.ledger.paymentledgerservice._interface.http.account;

import com.agon.ledger.paymentledgerservice._interface.http.mapper.HttpExceptionMapper;
import com.agon.ledger.paymentledgerservice._interface.http.request.CreateAccountRequest;
import com.agon.ledger.paymentledgerservice.application.commands.account.CreateAccountCommand;
import com.agon.ledger.paymentledgerservice.shared.domain_error.DomainError;
import libs.command.CommandBus;
import libs.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountWriteController {
    private final CommandBus commandBus;
    private final HttpExceptionMapper httpExceptionMapper;

    @PostMapping
    public ResponseEntity<UUID> createAccount(@RequestBody CreateAccountRequest request) {
        var command = new CreateAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        );

        Result<UUID, DomainError> result = commandBus.execute(command);

        if (result.isFailure()) {
            throw httpExceptionMapper.map(result.getError());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result.unwrap());
    }
}
