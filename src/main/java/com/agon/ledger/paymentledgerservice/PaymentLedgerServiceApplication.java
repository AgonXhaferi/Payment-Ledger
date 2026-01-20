package com.agon.ledger.paymentledgerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ComponentScan({
        "com.agon.ledger.paymentledgerservice", // Scan your main app
        "libs"                                  // Scan the libs folder
})
@EnableJpaAuditing
public class PaymentLedgerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentLedgerServiceApplication.class, args);
    }

}
