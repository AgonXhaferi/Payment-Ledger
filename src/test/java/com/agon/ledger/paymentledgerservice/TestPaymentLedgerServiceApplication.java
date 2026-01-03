package com.agon.ledger.paymentledgerservice;

import com.agon.ledger.paymentledgerservice.config.PaymentLedgerServiceApplication;
import org.springframework.boot.SpringApplication;

public class TestPaymentLedgerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(PaymentLedgerServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
