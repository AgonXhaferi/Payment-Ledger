package com.agon.ledger.paymentledgerservice.infrastructure.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry(order = Ordered.HIGHEST_PRECEDENCE)
public class RetryConfig {
}
