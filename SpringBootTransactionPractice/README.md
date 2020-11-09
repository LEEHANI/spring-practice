# Spring Boot @Transactional 
- Spring Boot, Jpa, H2 

## AOP와 Transactional 
- 스프링에서는 프록시 패턴을 AOP 기술을 사용한다 
- JDK Proxy
  + 인터페이스가 구현된 클래스에서만 사용. Thread-safe 
- CGLib Proxy 
  + 부모 인터페이스가 없어도 적용 가능. Thread-safe 
  
## Transactional 동작 
- service -> Proxy service
- transaction begin
- service method 실행
- transaction commit or rollback

## 동일클래스 @Transactional 테스트
### 메서드에서 트랜잭션 걸린 메서드 호출 시, 메서드2의 트랜잭션은 동작하지 않는다. 
```
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
```

### 트랜잭션 걸린 메서드에서 메서드 호출 시, 메서드1의 트랜잭션이 동작한다. 
```
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
```



## Transaction Exception 상황에서의 동작
- 참여 중인 트랜잭션이 실패하면 기본 정책이 전역롤백이다. rollback-only
  + A → B 메소드 호출되고 A 메소드에서 트랜잭션 시작되고 B 도 @Transactional(propagation=REQUIRED)일 경우, B에서 예외가 발생하면 A에서 비록 예외를 잡아서 먹어버리더라도 트랜잭션은 롤백 된다
- 트랜잭션에서 Propagation.REQUIRES_NEW로 새로운 트랜잭션을 만들면, 커넥션이 또 생성된다.   