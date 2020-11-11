package com.example.transaction.service;

import com.example.transaction.service.Interface.TransactionInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionInterfaceImplTest {

    @Autowired
    TransactionInterface transactionInterface;

    @Test
    void 인터페이스가_있는_트랜잭션() {
        transactionInterface.transactionMethod();
    }
}
