# 스프링 배치
- 일괄처리 애플리케이션 
- 인프런 스프링 배치(정수원님) - `Spring Boot 기반으로 개발하는 Spring Batch` 강의 듣고 정리 


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

## 메타 테이블
- 스프링 배치 실행 및 관리를 위해 배치 실행 사항들을 DB로 저장할 수 있음  
- 스키마 위치 `/org/springframework/batch/core/schema-*.sql`
- 수동 생성 - schema-mysql.sql 쿼리 복사해서 실행 
- 자동 생성 spring.batch.jdbc.initialize-schema 설정
  + `ALWAYS`: 스크립트 항상 실행. RDBMS -> 내장 DB 순서로 실행
  + `EMBEDDED`: 내장 DB일 때 스키마 자동 생성 
  + `NEVER`: 스크립트 실행 안함. 내장 DB일 경우 스크립트 생성이 안되기 때문에 오류 발생

### Job 관련 테이블 
- BATCH_JOB_INSTANCE
- BATCH_JOB_EXECUTION
- BATCH_JOB_EXECUTION_PARAMS
- BATCH_JOB_EXECUTION_CONTEXT

### Step 관련 테이블 
- BATCH_STEP_EXECUTION
- BATCH_STEP_EXECUTION_CONTEXT
