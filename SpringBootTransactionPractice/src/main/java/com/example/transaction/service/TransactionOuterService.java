package com.example.transaction.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Service
public class TransactionOuterService {
    @Autowired TransactionInnerService transactionInnerService;

    public void noTransactionMethodCallTransactionMethod() {
        log.info("----11--> {}, {}", TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        transactionInnerService.transaction();
    }

    @Transactional
    public void transactionMethodCallNoTransactionMethod() {
        log.info("----11--> {}, {}", TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        transactionInnerService.noTransaction();
    }

    @Transactional
    public void transactionMethodCallTransactionMethod() {
        log.info("----11--> {}, {}", TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        transactionInnerService.transaction();
    }

    @Transactional
    public void transactionMethodCallRequiresNewTransactionMethod() {
        log.info("----11--> {}, {}", TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        transactionInnerService.requiresNewTransaction();
    }
}
