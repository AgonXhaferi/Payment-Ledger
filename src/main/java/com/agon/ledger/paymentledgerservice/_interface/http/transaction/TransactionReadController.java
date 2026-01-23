package com.agon.ledger.paymentledgerservice._interface.http.transaction;

import com.agon.ledger.paymentledgerservice._interface.http.mapper.HttpExceptionMapper;
import com.agon.ledger.paymentledgerservice.application.queries.transaction.GetTransactionHistoryQuery;
import com.agon.ledger.paymentledgerservice.domain.entity.Transaction;
import libs.query.QueryBus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionReadController {
    private final QueryBus queryBus;
    private final HttpExceptionMapper httpExceptionMapper;

    @GetMapping("/{accountId}")
    public ResponseEntity<Page<Transaction>> getHistory(
            @PathVariable UUID accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var query = new GetTransactionHistoryQuery(accountId, page, size);

        var result = queryBus.execute(query);

        if (result.isFailure()) {
            throw httpExceptionMapper.map(result.getError());
        }

        return ResponseEntity.ok(result.unwrap());
    }
}
