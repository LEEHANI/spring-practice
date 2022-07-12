package com.example.springbatch.job.scope;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;

public class CustomStepListener implements org.springframework.batch.core.StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        stepExecution.getExecutionContext().putString("name2","user2");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
