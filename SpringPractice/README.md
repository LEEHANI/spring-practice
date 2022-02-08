- [의존관계 자동 주입 방법]
- [빈 스코프](./src/test/java/com/example/demo/scope/README.md) 


# Spring
- 스프링이란 loC와 AOP를 지원하는 경량의 컨테이너 프레임워크이다

## Ioc/DI
- 대신 관리해준다(loC)와 대신넣어준다(DI)는 뜻이다. 
  + 대신해주는 것은 미리 찜해놓은 객체를 생성하고 관계를 설정시켜주고 소멸시키는 것이다.
  + 빈을 만들고 엮어주며 제공해준다.
  + `의존성 주입은 빈 끼리만 가능`
  + 빈은 싱글 스코프 
  + `BeanFactory` : 빈들을 관리해주는 loc 
  + `ApplicationContext` : BeanFactory외에 추가적으로 다른일(트랜잭션, AOP, ApplicationEvent ...)도 해줌
- Bean 등록 방법
  - 빈이란 스프링 IoC 컨테이너가 관리하는 객체
    + new 해서 만든 인스턴스는 Bean이 아님 !
  - @Component Scanning
    + @Component 
      + @Controller
      + @Configuration
      + @Service
      + @Repository 
  - 빈으로 직접 등록 @Bean
- Bean 사용 방법
  - ApplicationContext에서 getBean()으로 직접 꺼내기 
  - @Autowired or @Inject
- 의존성 주입(Dependency Injection)
  + @Autowired or @Inject를 어디에 붙일까
    - 생성자(권장)
    - 필드
    - Setter  
 
## AOP(Aspect Oriented Programming)
- 흩어진 코드를 한 곳으로 모아 
```
class A 
{
  method a()
  {
    AAAA -> CC
    아침에 일어나서 출근했습니다. 
    BBBB
  }
  
  method b()
  {
    AAAA -> CC
    퇴근하고 저녁을 먹었습니다. 
    BBBB
  }
}
class B
{
  method c()
  {
     AAAA -> CC
     점심에 미팅이 있습니다. 
     BBBB
  }
}
``` 
- AAAA가 CC로 바뀐다면 찾아가면서 다 바꿔야함  
- 모으기 
```
class A 
{
  method a()
  {
    아침에 일어나서 출근했습니다. 
  }
  
  method b()
  {
    퇴근하고 저녁을 먹었습니다. 
  }
}
class B
{
  method c()
  {
     점심에 미팅이 있습니다. 
  }
}
class AAABBB
{
  method aaabbb(JoinPoint point)
  {
     AAAA -> CC
     point.execute()
     BBBB 
  }
}
``` 
- 구현 방법 
  + 컴파일 A.java --(AOP)--> A.class(AspectJ) 
  + 바이트코드 조작 A.java ----> A.class --(AOP)(class loader)--> 메모리(AspectJ) 
  + 프록시 패턴 (Spring AOP)
    + 기존 코드 건들지 않고 새 기능 추가하기


## MVC
## JDBC
## spring security
  - 자바 EE 기의 엔터프라이즈 소프트웨어 애플리케이션을 위한 포괄적인 보안 서비스들을 제공
  - 인증(Authentication)과 권한(Authorization)을 이해야한다.
    + ```인증```은 애플리케이션의 작업을 수행할 수 잇는 주체(사용자) 라는 것을 말함
    + ```권한```은 인증된 주체가 애플리케이션의동작을 수행할 수 있도록 허락되있는지   
      * 웹 요청 권한, 메소드 호출 및 도메인 인스턴스에 대한 접근 권한
      