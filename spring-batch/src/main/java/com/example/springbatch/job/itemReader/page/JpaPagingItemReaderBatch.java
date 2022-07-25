package com.example.springbatch.job.itemReader.page;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class JpaPagingItemReaderBatch {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private int chunkSize = 10;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job jpaPagingJob() throws Exception {
        return jobBuilderFactory.get("jpa-paging")
                .incrementer(new RunIdIncrementer())
                .start(jpaPagingStep())
                .build();
    }

    @Bean
    public Step jpaPagingStep() throws Exception {
        return stepBuilderFactory.get("jpa-paging")
                .<Member, Member>chunk(chunkSize)
                .reader(jpaPagingItemReader())
                .writer(new ItemWriter() {
                    @Override
                    public void write(List list) throws Exception {
                        System.out.println("list = " + list);
                    }
                })
                .build();
    }

    @Bean
    public ItemReader<Member> jpaPagingItemReader() throws Exception {
        return new JpaPagingItemReaderBuilder<Member>()
                .name("jpaPagingItemReader")
                .pageSize(chunkSize)
                .entityManagerFactory(entityManagerFactory)
                .queryString("select m from Member m join m.address")
                .build();
    }
}
