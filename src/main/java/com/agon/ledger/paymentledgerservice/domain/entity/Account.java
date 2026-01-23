package com.agon.ledger.paymentledgerservice.domain.entity;

import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;

import java.math.BigDecimal;

public class Account {
    private final AccountId id;
    private BigDecimal balance;
    private final String currency;
    private Long version;

    public static Account create(AccountId id, String currency) {
        return new Account(id, BigDecimal.ZERO, currency, null);
    }

    public Account(
            AccountId id,
            BigDecimal balance,
            String currency,
            Long version
    ) {
        this.id = id;
        this.balance = balance;
        this.currency = currency;
        this.version = version;
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (this.balance.subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
        this.balance = this.balance.subtract(amount);
    }

    public AccountId getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public Long getVersion() {
        return version;
    }
}
