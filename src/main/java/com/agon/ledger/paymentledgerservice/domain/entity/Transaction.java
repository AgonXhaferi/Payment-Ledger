package com.agon.ledger.paymentledgerservice.domain.entity;

import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;
import com.agon.ledger.paymentledgerservice.domain.value_object.TransactionStatus;
import com.agon.ledger.paymentledgerservice.shared.domain_error.DomainError;
import libs.result.Result;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Transaction {

    private final UUID id;
    private final AccountId sourceAccountId;
    private final AccountId targetAccountId;
    private final BigDecimal amount;
    private final String currency;
    private final String reference;
    private final String idempotencyKey;
    private final Instant createdAt;

    private TransactionStatus status;
    private Long version;
    private String failureReason;

    private Transaction(
            UUID id,
            AccountId sourceAccountId,
            AccountId targetAccountId,
            BigDecimal amount,
            String currency,
            String reference,
            String idempotencyKey,
            Instant createdAt,
            TransactionStatus status,
            Long version
    ) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
        this.currency = currency;
        this.reference = reference;
        this.idempotencyKey = idempotencyKey;
        this.createdAt = createdAt;
        this.status = status;
        this.version = version;
    }

    public static Result<Transaction, DomainError> create(
            AccountId source,
            AccountId target,
            BigDecimal amount,
            String currency,
            String reference,
            String idempotencyKey
    ) {
        if (source.equals(target)) {
            return Result.err(new DomainError.BadRequest("Cannot transfer money to the same account"));
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return Result.err(new DomainError.BadRequest("Transfer amount must be positive"));
        }

        return Result.ok(new Transaction(
                UUID.randomUUID(),
                source,
                target,
                amount,
                currency,
                reference,
                idempotencyKey,
                Instant.now(),
                TransactionStatus.PENDING,
                null
        ));
    }


    public static Transaction restore(
            UUID id,
            AccountId source,
            AccountId target,
            BigDecimal amount,
            String currency,
            String reference,
            String idempotencyKey,
            Instant createdAt,
            TransactionStatus status,
            Long version,
            String failureReason
    ) {
        var tx = new Transaction(
                id, source, target, amount, currency, reference, idempotencyKey, createdAt, status, version
        );

        tx.failureReason = failureReason;
        return tx;
    }


    public void complete() {
        if (this.status != TransactionStatus.PENDING) {
            throw new IllegalStateException("Cannot complete transaction that is already " + this.status);
        }
        this.status = TransactionStatus.COMPLETED;
    }

    public void fail(String reason) {
        // We might allow failing a COMPLETED transaction if it's a compensation,
        // but usually, you only fail PENDING ones.
        if (this.status != TransactionStatus.PENDING) {
            throw new IllegalStateException("Cannot fail transaction that is already " + this.status);
        }
        this.status = TransactionStatus.FAILED;
        this.failureReason = reason;
    }


    private static void validate(AccountId source, AccountId target, BigDecimal amount) {
        if (source.equals(target)) {
            throw new IllegalArgumentException("Cannot transfer money to the same account");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
    }
}