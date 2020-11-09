package com.example.transaction.service;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

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

//    PROPAGATION_REQUIRED 또는 PROPAGATION_SUPPORTS 인 참여 중인 트랜잭션이 실패하면, 그 트랜잭션은 전역적으로 rollback-only로 마킹된다.
    @Test
    void 트랜잭션_걸린_외부에서_런타입_예외_발생하는_메서드_호출() {
        assertThrows(RuntimeException.class, () -> transactionOuterService.noTransactionMethodCallThrowingRuntimeExceptionMethod());
    }
}