package com.example.transaction.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Service
public class TransactionInnerService {

    @Transactional
    public void transaction() {
        log.info("----22--> {}, {}", TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly());
    }

    public void noTransaction() {
        log.info("----22--> {}, {}", TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiresNewTransaction() {
        log.info("----22--> {}, {}", TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly());
    }
}
