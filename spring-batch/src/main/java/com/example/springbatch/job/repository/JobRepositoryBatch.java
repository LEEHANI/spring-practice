package com.example.springbatch.job.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobRepositoryBatch {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobRepositoryListener jobRepositoryListener;

    @Bean
    public Job batchJob() {
        return this.jobBuilderFactory.get("context job")
                .start(step1())
                .listener(jobRepositoryListener)
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
