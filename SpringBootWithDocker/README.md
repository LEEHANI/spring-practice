https://spring.io/guides/gs/spring-boot-docker/

# Spring Boot With Docker


## 개발환경
- java8
- Spring Boot


## gradle
- `./gradlew clean`
- `./gradlew build`


## docker
- `docker build --build-arg JAR_FILE=build/libs/*.jar -t spring-boot-with-docker .`


## Dockerfile
```
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```


## run
-` docker run -p 8080:8080 spring-boot-with-docker`
- `http://localhost:8080`

