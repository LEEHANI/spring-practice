package com.example.springbatch.job.scope;

import org.springframework.batch.core.JobExecution;

public class CustomJobListener implements org.springframework.batch.core.JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobExecution.getExecutionContext().putString("name", "user1");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

    }
}
