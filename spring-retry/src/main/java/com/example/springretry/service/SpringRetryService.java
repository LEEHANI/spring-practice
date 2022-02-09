package com.example.springretry.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpringRetryService {

    private final RetryTemplate retryTemplate;

    static int RETRY_COUNT = 0;

    public SpringRetryService(RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
    }

    @Retryable(value = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000))
    public String retry() throws Exception {
        System.out.println("retry " + RETRY_COUNT++);
        exceptionMethod();
        return "retry " + RETRY_COUNT;
    }

    private void exceptionMethod() throws Exception {
        throw new Exception();
    }

    @Recover
    private String recover(Exception e) {
        System.out.println("recover");
        return "recover " + RETRY_COUNT;
    }

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
}
