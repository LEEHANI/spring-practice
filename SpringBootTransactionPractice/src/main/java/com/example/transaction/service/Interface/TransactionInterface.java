package com.example.transaction.service.Interface;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TransactionInterface {
    public void transactionMethod();
}
