package com.example.transaction.service;

import com.example.transaction.entity.Member;
import com.example.transaction.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionSameService {
    private final MemberRepository userRepository;

    public List<Member> noTransactionMethodCallTransactionMethod() {
        log.info("----11--> {}, {}", TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        List<Member> members = userRepository.findAll();
        transaction(members);
        return members;
    }

    @Transactional
    public void transaction(List<Member> members) {
        log.info("----22--> {}, {}", TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        members.forEach(u->u.changeName(u.getName() + " 변경"));
    }

    //

    @Transactional
    public List<Member> transactionMethodCallNoTransactionMethod() {
        log.info("----11--> {}, {}", TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        List<Member> members = userRepository.findAll();
        noTransaction(members);
        return members;
    }


    public void noTransaction(List<Member> members) {
        log.info("----22--> {}, {}", TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        members.forEach(u->u.changeName(u.getName() + " 변경"));
    }
}
