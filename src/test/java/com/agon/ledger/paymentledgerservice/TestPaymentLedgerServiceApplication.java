package com.agon.ledger.paymentledgerservice;

import org.springframework.boot.SpringApplication;

public class TestPaymentLedgerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(PaymentLedgerServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
