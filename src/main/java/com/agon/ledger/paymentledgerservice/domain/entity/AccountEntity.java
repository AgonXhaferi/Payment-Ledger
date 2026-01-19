package com.agon.ledger.paymentledgerservice.domain.entity;

import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;

import java.math.BigDecimal;

public class AccountEntity {
    private final AccountId id;
    private BigDecimal balance;
    private final String currency;
    private Long version;

    public static AccountEntity create(AccountId id, String currency) {
        return new AccountEntity(id, BigDecimal.ZERO, currency, 0L);
    }

    public AccountEntity(
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

    public AccountId getId() { return id; }
    public BigDecimal getBalance() { return balance; }
    public String getCurrency() { return currency; }
    public Long getVersion() { return version; }
}
