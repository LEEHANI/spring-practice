
# spring-retry
- 실패한 동작을 `재호출` 해주는 기능을 지원해준다. 
- ex) restTemplate으로 외부 api 호출 실패 시, 여러번 재호출하도록 동작하게 할 수 있다. 

## spring-retry 적용 
- gradle
  ```
  implementation 'org.springframework.retry:spring-retry'
  implementation 'org.springframework:spring-aspects' 
  ```
- Application에 `@EnableRetry` 어노테이션 추가 
  ``` 
  @EnableRetry
  @SpringBootApplication
  public class SpringRetryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRetryApplication.class, args);
    }
  }
  ```
- 적용할 서비스에 `@Retryable` 어노테이션 추가. 
  ```
  @Retryable(value = Exception.class, 
            maxAttempts = 5, 
            backoff = @Backoff(delay = 2000))
  public String retry() throws Exception {
     System.out.println("retry " + RETRY_COUNT++);
     exceptionMethod();
     return "retry " + RETRY_COUNT;
  } 
  ```
- value에 지정한 `Exception` 발생시 `retry`를 시도한다. 최대 `5회` 시도하고 재시도 딜레이는 `2초`로 설정. default는 3 times, 1s delay
- 재시도가 다 실패하면 복구 메서드를 실행할 수 있다. `@Recover` 함수가 실행된다.
  ```
  @Recover
  public String recover(Exception e) {
        System.out.println("recover");
        return "recover " + RETRY_COUNT;
  } 
  ```
- `recover` 함수가 실행되기 위해서는 위에 지정한 `@Retryable`함수의 `return type`, `method args`와 `exception`을 인자로 받아야함. 

## RetryTemplate
- `@Retryable`로 메서드마다 지정할 수도 있지만 `빈`으로 만들어서 글로벌로 사용할 수 있다. 
    ```
    @Bean
    public RetryTemplate retryTemplate() {
        return RetryTemplate.builder()
                .maxAttempts(5)
                .retryOn(Exception.class)
                .build();
    } 
    ```
- RetryTemplate은 RetryOperation 인터페이스를 구현해서 사용해야 한다. 
```
  public interface RetryOperations {
  
      <T> T execute(RetryCallback<T> retryCallback) throws Exception;
  
      <T> T execute(RetryCallback<T> retryCallback, RecoveryCallback<T> recoveryCallback)
          throws Exception;
  
      <T> T execute(RetryCallback<T> retryCallback, RetryState retryState)
          throws Exception, ExhaustedRetryException;
  
      <T> T execute(RetryCallback<T> retryCallback, RecoveryCallback<T> recoveryCallback,
          RetryState retryState) throws Exception;
  
  } 
```
- 서비스에 retryTemplate() 메서드 추가해서 빈으로 만든 RetryTemplate를 사용해보자.    
    ``` 
    public String retryTemplate() throws Exception {
        String retryTemplate = this.retryTemplate.execute(retry -> {
            System.out.println("retryTemplate");
            exceptionMethod();
            return "test";
        }, recover -> {
            System.out.println("retryTemplate recover. retry count: " + recover.getRetryCount());
            return "recover";
        });

        System.out.println(retryTemplate);

        return "retryTemplate";
    }
    ```

## Listener
- `retry`는 유용한 리스너를 제공한다.
  ``` 
  public interface RetryListener {

    void open(RetryContext context, RetryCallback<T> callback);

    void onError(RetryContext context, RetryCallback<T> callback, Throwable e);

    void close(RetryContext context, RetryCallback<T> callback, Throwable e);
  }
  ```
- `open`, `close`는 `retry` 재시도 전, 후에 발생하고 `onError`는 개별 retry 사이에 호출된다. 
- 간단하게 로그만 찍어서 리스너를 등록해 확인해봤다. 
  ```
  @Component
  public class RetryTemplateListener extends RetryListenerSupport {
  
    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        System.out.println("retry listener close");
        super.close(context, callback, throwable);
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        System.out.println("retry listener error");
        super.onError(context, callback, throwable);
    }

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        System.out.println("retry listener open");
        return super.open(context, callback);
    }
  }
  ```
  ```
    @Bean
    public RetryTemplate retryTemplate() {
        return RetryTemplate.builder()
                .maxAttempts(5)
                .retryOn(Exception.class)
                .withListener(listener) // 리스너 등록
                .build();
    } 
  ```
- 결과 
  ```
  retry listener open
  retry 0
  retry listener error
  retry 1
  retry listener error
  retry 2
  retry listener error
  recover
  retry listener close 
  ```
- recover 함수까지 호출 된 후에 close가 찍힌걸 볼 수 있다. 

## 동작 테스트
- http://localhost:8080/retry
- http://localhost:8080/retry-template


## ref
- https://github.com/spring-projects/spring-retry