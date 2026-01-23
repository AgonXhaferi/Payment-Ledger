package com.agon.ledger.paymentledgerservice._interface.http.transaction;


import com.agon.ledger.paymentledgerservice._interface.http.mapper.HttpExceptionMapper;
import com.agon.ledger.paymentledgerservice._interface.http.request.TransferFundsRequest;
import com.agon.ledger.paymentledgerservice.application.commands.transaction.TransferFundsCommand;
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
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionWriteController {
    private final CommandBus commandBus;
    private final HttpExceptionMapper httpExceptionMapper;

    @PostMapping
    public ResponseEntity<UUID> transferFunds(@RequestBody TransferFundsRequest request) {
        var command = new TransferFundsCommand(
                request.sourceAccountId(),
                request.targetAccountId(),
                request.amount(),
                request.currency(),
                request.reference(),
                request.idempotencyKey()
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
