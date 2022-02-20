# 스프링 배치
- 일괄처리 애플리케이션 
- 인프런 스프링 배치(정수원님) - Spring Boot 기반으로 개발하는 Spring Batch 강의 듣고 정리 


## 핵심 패턴
- Read: DB, 큐, 파일 등에서 데이터 조회
- Process
- Write

## 배치 시나리오
- 대용량 병렬 처리
- 실패 후 수동 또는 스케줄링에 의한 재시작
- 의존관계 step을 순차적으로 처리

## 스프링 배치 초기화 설정 클래스 
- BatchAutoConfiguration 
  + 스프링 배치가 초기화 될 때 자동으로 실행되는 설정 클래스 
  + Job을 수행하는 JobLauncherApplicationRunner 빈 생성 
- SimpleBatchConfiguration
  + JobBuilderFactory와 StepBuilderFactory 생성
  + 스프링 배치의 주요 구성 요소 생성 - `프록시 객체`로 생성됨 
- BatchConfigurerConfiguration 
  + BasicBatchConfigurer
    - SimpleBatchConfiguration에서 생성한 프록시 객체의 실제 대상 객체를 생성하는 설정 클래스  
  + JpaBatchConfigurer
  + 사용자 정의 BatchConfigurer 인터페이스를 구현하여 사용할 수 있음. 


## hello batch 
- Job이 구동되면 Step을 실행하고 Step이 구동되면 Taskelt(작업내용)을 실행하도록 설정함 


