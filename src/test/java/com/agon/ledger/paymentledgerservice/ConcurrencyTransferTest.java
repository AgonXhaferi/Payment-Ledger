package com.agon.ledger.paymentledgerservice;

import com.agon.ledger.paymentledgerservice.application.commands.account.CreateAccountCommand;
import com.agon.ledger.paymentledgerservice.application.commands.transaction.TransferFundsCommand;
import com.agon.ledger.paymentledgerservice.application.ports.account.read.LoadAccountPort;
import com.agon.ledger.paymentledgerservice.domain.value_object.AccountId;
import libs.command.CommandBus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@Testcontainers
public class ConcurrencyTransferTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private CommandBus commandBus;

    @Autowired
    private LoadAccountPort loadAccountPort;

    @Test
    void shouldHandleConcurrentTransfersWithoutLosingMoney() throws InterruptedException {
        var sourceId = UUID.randomUUID();
        var targetId = UUID.randomUUID();

        commandBus.execute(new CreateAccountCommand(
                sourceId, new BigDecimal("1000.00"), "USD"
        ));

        commandBus.execute(new CreateAccountCommand(
                targetId, new BigDecimal("0.00"), "USD"
        ));

        int threadCount = 10;
        var executor = Executors.newFixedThreadPool(threadCount);

        var startLatch = new CountDownLatch(1);

        var finishLatch = new CountDownLatch(threadCount);

        var successCount = new AtomicInteger(0);
        var failCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    startLatch.await(); // Wait for the gun

                    var result = commandBus.execute(new TransferFundsCommand(
                            sourceId,
                            targetId,
                            new BigDecimal("10.00"),
                            "USD",
                            "Concurrent Test",
                            UUID.randomUUID().toString()
                    ));

                    if (result.isSuccess()) {
                        successCount.incrementAndGet();
                    } else {
                        failCount.incrementAndGet();
                        System.out.println("Failed: " + result.getError().message());
                    }
                } catch (Exception e) {
                    failCount.incrementAndGet();
                    e.printStackTrace();
                } finally {
                    finishLatch.countDown();
                }
            });
        }

        startLatch.countDown();

        boolean allFinished = finishLatch.await(100, TimeUnit.SECONDS);

        executor.shutdown();

        Assertions.assertTrue(allFinished, "Not all threads finished in time!");

        var source = loadAccountPort.loadAccount(new AccountId(sourceId)).orElseThrow();
        var target = loadAccountPort.loadAccount(new AccountId(targetId)).orElseThrow();

        System.out.println("Source Balance: " + source.getBalance());
        System.out.println("Target Balance: " + target.getBalance());
        System.out.println("Successes: " + successCount.get());
        System.out.println("Failures: " + failCount.get());

        Assertions.assertEquals(10, successCount.get(), "All transfers should succeed");
        Assertions.assertEquals(0, failCount.get());
        Assertions.assertEquals(0, new BigDecimal("900.00").compareTo(source.getBalance()));
        Assertions.assertEquals(0, new BigDecimal("100.00").compareTo(target.getBalance()));
    }
}