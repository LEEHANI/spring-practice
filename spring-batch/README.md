# 스프링 배치
- `일괄처리 애플리케이션` 
- `인프런` `스프링 배치(정수원님)` - `Spring Boot 기반으로 개발하는 Spring Batch` 강의 듣고 정리 

## 핵심 패턴
- Read: DB, 큐, 파일 등에서 데이터 조회
- Process
- Write

## 배치 시나리오
- 대용량 병렬 처리
- 실패 후 수동 또는 스케줄링에 의한 재시작
- 의존관계 step을 순차적으로 처리

## 스프링 배치 초기화 설정 클래스 
- `BatchAutoConfiguration` 
  + 스프링 배치가 초기화 될 때 자동으로 실행되는 설정 클래스 
  + Job을 수행하는 JobLauncherApplicationRunner 빈 생성 
- `SimpleBatchConfiguration`
  + JobBuilderFactory와 StepBuilderFactory 생성
  + 스프링 배치의 주요 구성 요소 생성 - `프록시 객체`로 생성됨 
- `BatchConfigurerConfiguration` 
  + BasicBatchConfigurer
    - SimpleBatchConfiguration에서 생성한 프록시 객체의 실제 대상 객체를 생성하는 설정 클래스  
  + JpaBatchConfigurer
  + 사용자 정의 BatchConfigurer 인터페이스를 구현하여 사용할 수 있음. 

## 배치 적용하기 
- gradle 
  ``` 
  implementation 'org.springframework.boot:spring-boot-starter-batch'
  ```
- `@EnableBatchProcessing` 어노테이션 추가 
  ```
  @SpringBootApplication
  @EnableBatchProcessing
  public class SpringBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
    }

  }
  ```
  
## 배치 맛보기 Hello Batch
  ```
  @RequiredArgsConstructor
  @Configuration
  public class HelloJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob")
                .start(helloStep1())
                .next(helloStep2())
                .build();
    }


    @Bean
    public Step helloStep2() {
        return stepBuilderFactory.get("helloStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

                        System.out.println("=========================");
                        System.out.println(" >> Hello Spring Batch!!");
                        System.out.println("=========================");

                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step helloStep1() {
        return stepBuilderFactory.get("helloStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

                        System.out.println("=========================");
                        System.out.println(" >> Step2 Spring Batch!!");
                        System.out.println("=========================");

                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
  } 
  ```
- `Job`이 구동되면 `Step`을 실행하고 Step이 구동되면 `Taskelt`(작업내용)을 실행하도록 설정함

## 메타 테이블
![MetaTable](./images/MetaTable.png)
- 스프링 배치 실행 및 관리를 위해 배치 실행 사항들을 DB에 저장할 수 있음  
- 스키마 위치 `/org/springframework/batch/core/schema-*.sql`
- 기본으로 `h2같은 embedded db를 사용하면 테이블이 자동으로 생성` 된다.
- `MySQL`을 사용하려면 `schema-mysql.sql`를 복사해서 테이블을 생성해야 한다.
- 테이블 생성 옵션. spring.batch.jdbc.initialize-schema
  + `ALWAYS`: 스크립트 항상 실행. RDBMS -> 내장 DB 순서로 실행
  + `EMBEDDED`: 내장 DB일 때 스키마 자동 생성 
  + `NEVER`: 스크립트 실행 안함. 내장 DB일 경우 스크립트 생성이 안되기 때문에 오류 발생

## MySQL로 메타 테이블 관리 
- 디비 연결 
![mysql-connection](./images/mysql-connection.png)
![mysql-mata-table](./images/mysql-mata-table.png)
- application.yml 설정 
```
spring:
  profiles:
    active: mysql

---
spring:
  config:
    activate:
      on-profile: local
  datasource:
    hikari:
      jdbc-url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
      username: sa
      password:
      driver-class-name: org.h2.Driver
  batch:
    jdbc:
      initialize-schema: embedded
---
spring:
  config:
    activate:
      on-profile: mysql
  datasource:
    hikari:
      jdbc-url: jdbc:mysql://localhost:3306/springbatch?useUnicode=true&characterEncoding=utf8
      username: root
      password: 1
      driver-class-name: com.mysql.cj.jdbc.Driver
  batch:
    jdbc:
      initialize-schema: always
```

### Job 관련 테이블 
- BATCH_JOB_INSTANCE
- BATCH_JOB_EXECUTION
- BATCH_JOB_EXECUTION_PARAMS
- BATCH_JOB_EXECUTION_CONTEXT

### Step 관련 테이블 
- BATCH_STEP_EXECUTION
- BATCH_STEP_EXECUTION_CONTEXT

# Job
- 배치 계층 구조에서 가장 상위에 있는 개념. 하나의 배치 작업 자체를 의미함. 
- 작업의 단위. 최상위 인터페이스. 
- 여러 Step을 포함하고 있는 컨테이너. 반드시 한개 이상의 Step으로 구성해야함.

## Job 구현체 
- `SimpleJob`
  + 순차적으로 Step을 실행시키는 Job
  + 표준 기능
- `FlowJob`
  + 특정한 조건과 흐름에 따라 Step을 구성하여 실행시키는 Job 
  + Flow 객체를 실행시켜서 작업을 진행 
  
# JobInstance
![JobInstance](./images/JobInstance.png) (출처: 인프런 스프링 배치(정수원) 강의 노트 중 일부분)
- `JobInstance`는 `Job이 실행될 때 생성`되고, `Job의 논리적 실행 단위`로서 작업 현황을 디비에 저장하는 메타데이터
- `Job`의 `설정과 구성은 동일해도 실행되는 시점에 처리하는 내용(Job, JobParameter)은 다르기 때문에 Job의 실행을 구분`해야한다. 
- 최초 실행하면 Job(jobName), JobParameter(jobKey) 정보가 JobInstance로 생성되어 JOB_INSTANCE 테이블에 저장된다.
- 이전과 동일한 정보인 경우 기존 JobInstance 정보를 얻는다. (동일한 내용으로는 수행 불가)  
- 즉, 똑같은 JobInstance는 하나밖에 없다. 

## BATCH_JOB_INSTANCE
- 간단한 job을 만들어서 구동시켜보자.  
- ```java
  @Configuration
  @RequiredArgsConstructor
  public class JobConfiguration {
      private final JobBuilderFactory jobBuilderFactory;
      private final StepBuilderFactory stepBuilderFactory;
  
      @Bean
      public Job job() {
          return jobBuilderFactory.get("job")
                  .start(step1())
                  .build();
      }
  
      @Bean
      public Step step1() {
          return stepBuilderFactory.get("step1")
                  .tasklet(new Tasklet() {
                      @Override
                      public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                          System.out.println("step1 was executed");
                          return RepeatStatus.FINISHED;
                      }
                  })
                  .build();
      }
  }
  ```
- BATCH_JOB_INSTANCE를 조회해보면 구동시킨 job 정보가 들어있다. 
- ![job-instance-table](./images/job-instance-table.png)
- `JOB_NAME`은 `jobBuilderFactory.get("job")`이고 `JOB_KEY`는 `jobParameters에 해시`를 적용한 값이다. 
- job을 실행할 때 외부에서 파라미터 값을 받아서 jobParamter값으로 사용할 수 있다.
- 위에 설명한 것처럼, `동일한 job에 대해서는 BATCH_JOB_INSTANCE에 기록되지 않는다.` 
- 배치를 다시 구동시켜보면, 데이터가 그대로인걸 볼 수 있다.
- 파라미터를 넘겨주기 위해서 `JobLauncher`로 job을 구동시켜보면 job, jobParameter 값이 기존에 없기에 JobInstance가 새로 생성된다. 
```
@Component
public class JobRunner implements ApplicationRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("param", "test")
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}
```
- ![JobInstance-with-param](./images/JobInstance-with-param.png)
- ![JobInstance-execution-params](./images/JobInstance-execution-params.png)

# JobParameter 
- Job을 실행할 때 사용되는 파라미터
- JobInstance를 구분하기 위한 용도 
- JobParamters와 JobInstance는 1:1 관계
- 파라미터 타입은 String, double, date, long 이다.  

## 생성 및 바인딩 
- 어플리케이션 실행 시 주입
  + `java -jar spring-batch-0.0.1-SNAPSHOT.jar stringParam=test2 longParam(long)=2L`
- 코드로 생성
  + JobParameterBuilder, DefaultJobParametersConverter
  + ```
    JobParameters jobParameters = new JobParametersBuilder()
                .addString("stringParam", "test")
                .addLong("longParam", 1L)
                .toJobParameters(); 
    ```
- SpEL 이용 
  + @Value("#{jobParameters[datetime]}"), @JobScope, @StepScope 선언 필수 

# JobExecution
- `JobInstance`를 `한 번 시도` 하는 것을 의미하는 객체. Job 실행 중에 발생한 정보들을 저장하는 객체 
- JobInstance 와 JobExecution 는 1:M 의 관계로서 JobInstance 에 대한 성공/실패의 내역을 가지고 있음

## BATCH_JOB_EXECUTION
- 최초 `Job`을 만들어서 파라미터를 `name=user1`로 전달하여 돌려보면 다음과 같다.
- ```java
  @Configuration
  @RequiredArgsConstructor
  public class JobExecutionBatch {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                        System.out.println("job execution step1 was executed");
                        return RepeatStatus.FINISHED;
                })
                .build();
    }
  }
  ```
- ![first-job-instance](./images/job-execution/first-job-instance.png)
- ![first-job-execution](./images/job-execution/first-job-execution.png)
- status=`COMPLETED`로 되어있다.
- 여기서 재구동하면 동일한 job instance가 존재하니, 파라미터를 바꾸라는 에러가 뜬다.  
- ![first-error](./images/job-execution/first-error.png)
- 이번엔 스텝에 에러를 추가해보자. `next(step2())` 파라미터를 name=user2로 전달.
- ```
    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                        System.out.println("job execution step1 was executed");
                        return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("job execution step2 was executed");
                    throw new RuntimeException("step2 has failed");
                })
                .build();
    }  
  ```
- ![second-job-execution](./images/job-execution/second-job-execution.png)
- 실행시켜보면 status=`FAILED`로 저장되어있다. 
- 이때 다시 실행시켜보자. 
- ![third-job-execution](./images/job-execution/third-job-execution.png)
- COMPLETED이 아닌 FAILED로 끝났기 때문에 동일한 job과 파라미터로 시도해도 데이터가 추가된다. 
- step2()에 exception 부분을 return RepeatStatus.FINISHED로 바꾸고 실행시켜보자. 
- ![fourth-job-execution](./images/job-execution/fourth-job-execution.png)
- 이번엔 `FAILED` 대신에 `COMPLETED`로 바뀌어있다. 
- COMPLETED일때 다시 실행시켜보면 `A job instance already exists and is complete for parameters={name=user2}.  If you want to run this job again, change the parameters.` 에러가 발생한다.   
- `즉, 동일한 job, jobParameters에 대해서 성공하면 다음번에 실행이 안되고, 실패하면 job_execution에 계속 추가되어 계속 시도한다.` 


# Step
![step_architecture](./images/step_architecture.png)
- Job을 구성하는 독립적인 하나의 단계
- 실제 비즈니스 로직이 들어감 

## 기본 구현체 
- TaskletStep
  + 기본 클래스. Tasket 타입의 구현체들을 제어 
- PartitionStep
  + 멀티스레드 방식으로 Step을 여러개 분리해서 실행 
- JobStep
  + Step 내에서 Job을 실행 
- FlowStep
  + Step 내에서 flow를 실행 

# StepExecution
- Step을 한 번의 시도를 의미하는 객체로서 Step 실행 중에 발생한 정보들을 저장하는 객체 
- Step이 시도될때 생성되고, 각 Step 별로 생성
- Job이 재시작 하더라도 이미 completed한 Step은 재실행 되지 않고 failed한 Step만 실행됨 
- Step이 하나라도 실패하면 JobExecution은 failed로 저장되고, 모든 Step이 성공하면 JobExecution은 completed로 저장된다.
- JobExecution과 StepExecution은 1:M 관계 

## BATCH_STEP_EXECUTION
- step을 3개 갖는 job을 실행.  
- ![step-execution1](./images/step-execution/step-execution1.png)
- ![job-execution1](./images/step-execution/job-execution1.png)
- 실행 결과를 보면 step 3개가 completed고 JobExecution이 completed로 되어있음.
- 이번엔 step2에 에러를 발생시키고 파라미터 값을 바꿔서 실행 
- ![step-execution2](./images/step-execution/step-execution2.png)
- ![job-execution2](./images/step-execution/job-execution2.png)
- 실행 결과를 보면, StepExecution step2 에서 FAILED가 발생했다. 실패한 step까지만 진행했고, step3는 진행되지 않은걸 볼 수 있다. JobExecution은 FAILED로 저장되어 있다.
  step2에 exception을 주석처리하고 return RepeatStatus.FINISHED로 바꾸서 다시 실행시켜보자.
- ![step-execution3](./images/step-execution/step-execution3.png)
- ![job-execution3](./images/step-execution/job-execution3.png)
- 실행 결과를 보면, 실패한 step2부터 실행하여 나머지 step3까지 수행했다. JobExecution은 COMPLETED 로 저장되어 있다.
- `즉, 하나의 Job은 여러 개의 Step으로 구성되고, 도중에 Step이 실패하면 성공적으로 완료된 step은 재 실행되지 않고 실패한 step 만 실행된다.` 

# StepContribution
- 청크 프로세스 변경 사항을 저장해뒀다가 apply 메서드를 통해 StepExecution에 상태를 업데이트 하는 도메인 객체 
- ExitStatus는 사용자 정희하여 사용할 수 있음. 

# ExecutionContext
- `StepExecution` or `JobExecution` 객체의 상태를 저장하는 `공유 객체` 
- 키/값으로 된 map 형태 
- StepExecution
  + 각 Step의 StepExecution에 저장되며 Step 간 서로 공유 안됨
- JobExeuction
  + 각 Job의 JobExecution에 저장되며 Job간 서로 공유는 안되며 해당 Job의 Step간 서로 공유됨 
![execution-context](./images/execution-context.png) (출처: 인프런 스프링 배치(정수원) 강의 노트 중 일부분)

# JobRepository 
- 배치 작업 중 정보를 저장하는 메타 데이터
- @EnableBatchProcessing 어노테이션을 선언하면 JobRepositroy가 빈으로 생성됨
- ```
  @Component
  @Slf4j
  public class JobRepositoryListener implements JobExecutionListener {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "user4")
                .toJobParameters();


        JobExecution lastJobExecution = jobRepository.getLastJobExecution(jobName, jobParameters);
        if(lastJobExecution != null) {
            for(StepExecution execution : lastJobExecution.getStepExecutions()) {
                BatchStatus status = execution.getStatus();
                ExitStatus exitStatus = execution.getExitStatus();

                log.info("status = {}, exitStatus = {}", status, exitStatus);
            }
        }
    }
  }
  ```
- JobExecutionListener를 통해서 job이 끝날때 jobRepositroy에서 배치 작업에 대한 정보를 이것 저것 볼 수 있다. 
- 