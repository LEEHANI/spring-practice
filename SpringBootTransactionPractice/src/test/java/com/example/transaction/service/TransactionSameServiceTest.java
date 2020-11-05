package com.example.transaction.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionSameServiceTest {

    @Autowired
    TransactionSameService transactionSameService;

    @Test
    void 동일클래스_메서드1에서_트랜잭션_걸린_메서드2_호출_시_테스트() {
        transactionSameService.noTransactionMethodCallTransactionMethod();
    }

    @Test
    void 동일클래스_트랜잭션_걸린_메서드1에서_메서드2_호출_시_테스트() {
        transactionSameService.transactionMethodCallNoTransactionMethod();
    }
}