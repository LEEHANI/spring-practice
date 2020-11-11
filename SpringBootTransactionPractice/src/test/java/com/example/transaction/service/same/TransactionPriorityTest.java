package com.example.transaction.service.same;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionPriorityTest {
    @Autowired
    TransactionPriority transactionPriority;

    @Test
    void 트랜잭션_우선순위() {
        transactionPriority.priority();
    }

}