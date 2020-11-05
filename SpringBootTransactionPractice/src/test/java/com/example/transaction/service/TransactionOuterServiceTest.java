package com.example.transaction.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionOuterServiceTest {
    @Autowired TransactionOuterService transactionOuterService;

    @Test
    void 트랜잭션_없는_외부에서_트랜잭션_걸린_내부_호출() {
        transactionOuterService.noTransactionMethodCallTransactionMethod();
    }

    @Test
    void 트랜잭션_걸린_외부에서_트랜잭션_없는_내부_호출() {
        transactionOuterService.transactionMethodCallNoTransactionMethod();
    }

    @Test
    void 트랜잭션_걸린_외부에서_트랜잭션_걸린_내부_호출() {
        transactionOuterService.transactionMethodCallTransactionMethod();
    }

    @Test
    void 트랜잭션_걸린_외부에서_requires_new_트랜잭션_걸린_내부_호출() {
        transactionOuterService.transactionMethodCallRequiresNewTransactionMethod();
    }
}