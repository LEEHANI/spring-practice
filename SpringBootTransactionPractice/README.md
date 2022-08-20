# Spring Boot @Transactional 
- Spring Boot, Jpa, H2 


## AOP와 Transactional 
- 스프링에서는 `프록시 패턴을 AOP 기술`을 사용한다 
- JDK Proxy
  + 인터페이스가 구현된 클래스에서만 사용. Thread-safe 
- `CGLib Proxy`
  + 런타임 시점에 프록시 객체를 만듦
  + 부모 인터페이스가 없어도 적용 가능. Thread-safe. 
  + Spring boot에서 transaction 대상의 기본 프록시 
- proxy-target-class: false (default)
  + proxy 모드에만 적용된다. @Transactional 어노테이션이 붙은 클래스에 어떤 타입의 트랜잭션 프록시를 생성할 것인지 제어한다. 
  + 공식문서에서는 proxy-target-class가 false이면 JDK 인터페이스 기반 프록시가 생성된고 한다. 하지만 Spring Boot에서 이 속성을 생략하면 @ConditionalOnProperty(matchIfmissing = true)에서 의해 proxy-target-class값이 true로 바뀐다. property에 명시적으로 설정하면 바뀌지 않는다. 
    - https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#tx-propagation
  + proxy-target-class가 true면, 클래스기반의 프록시(CGLib)가 생성된다.    

## Transactional 동작 
- Service -> Proxy service
- Transaction begin
- Service method 실행
- Transaction commit or rollback

## Transaction
- 스프링팀은 @Transactional 어노테이션을 인터페이스 보다 클래스에 선언하는 걸 권장한다. 
- class보다 method에 붙인 트랜잭션의 우선순위가 더 높다.
```
@Service
@Transactional(readOnly = false)
public class TransactionPriority {
    @Transactional(readOnly = true) //우선순위 높음 
    public void priority() {
        log.info("----11--> {}, {}", TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly());
    }
}
```

## Transaction custom
- 스프링 메타어노에티션을 이용해 커스텀할 수 있다. 
```
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(transactionManager = "member", readOnly = true)
public @interface MemberTransaction {
} 
```

## Transaction Propagation
### default `Propagation.REQUIRED` 
- 활성 트랜잭션이 없으면 새 트랜잭션을 생성. 

### Propagation.SUPPORTS 
- 활성 트랜잭션이 있으면 기존꺼 사용. 없으면 트랜잭션 없이 실행 

### Propagation.MANDATORY 
- 활성 트랜잭션이 없으면 예외 발생. 

### Propagation.NEVER
- 활성 트랜잭션 있으면 예외 발생. 

### Propagation.REQUIRES_NEW
- 항상 새 트랜잭션 생성 

### Propagation.NESTED
- 활성 트랜잭션이 있으면 저장점을 표시. 
- 비즈니스 로직에서 예외 발생 시 저장 지점으로 롤백
- 활성 트랜잭션이 없으면 REQUIRED 처럼 작동 

## isolation 
- default. DBMS의 기본 격리 수준 적용. 


## 동일클래스 @Transactional 테스트
### 메서드(transaction X)에서 트랜잭션 걸린 메서드 호출 시, 메서드2의 트랜잭션은 동작하지 않는다. 
```
public List<Member> noTransactionMethodCallTransactionMethod() {
    log.info("----11--> {}, {}", TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly());
    List<Member> members = userRepository.findAll();
    transaction(members);
    return members;
}

@Transactional //동작 안함
public void transaction(List<Member> members) {
    log.info("----22--> {}, {}", TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly());
    members.forEach(u->u.changeName(u.getName() + " 변경"));
}
```

### 트랜잭션 걸린 메서드에서 메서드(transaction X) 호출 시, 메서드1의 트랜잭션이 동작한다. 
```
@Transactional
public List<Member> transactionMethodCallNoTransactionMethod() {
    log.info("----11--> {}, {}", TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly());
    List<Member> members = userRepository.findAll();
    noTransaction(members);
    return members;
}

// 위의 트랜잭션 동작 
public void noTransaction(List<Member> members) {
    log.info("----22--> {}, {}", TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly());
    members.forEach(u->u.changeName(u.getName() + " 변경"));
}
```



## Transaction Exception 상황에서의 동작
- 참여 중인 트랜잭션이 실패하면 기본 정책이 `전역롤백`이다. rollback-only
  + A → B 메소드 호출되고 A 메소드에서 트랜잭션 시작되고 B 도 @Transactional(propagation=REQUIRED)일 경우, B에서 예외가 발생하면 A에서 비록 예외를 잡아서 먹어버리더라도 트랜잭션은 롤백 된다
- 트랜잭션에서 `Propagation.REQUIRES_NEW`로 새로운 트랜잭션을 만들면, 커넥션이 또 생성된다.   
