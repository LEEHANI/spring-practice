package com.example.transaction.service;

import com.example.transaction.entity.Member;
import com.example.transaction.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Slf4j
@Service
public class TransactionOuterService {
    @Autowired TransactionInnerService transactionInnerService;
    @Autowired MemberRepository memberRepository;

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

    /**
     * 참여 중인 일부트랜잭션이 실패하면, rollback-only에 마킹되고 전체 트랜잭션이 다 롤백된다.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void noTransactionMethodCallThrowingRuntimeExceptionMethod() {
        log.info("----11--> {}, {}, {}", TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly(), TransactionSynchronizationManager.getCurrentTransactionIsolationLevel());
        try {
            transactionInnerService.throwingRuntimeException();
        } catch (RuntimeException e) {
            log.info("Catch RuntimeException.");
        }

        List<Member> members = memberRepository.findAll();
        return;
    }

}
