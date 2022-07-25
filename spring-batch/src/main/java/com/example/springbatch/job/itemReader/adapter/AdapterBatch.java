package com.example.springbatch.job.itemReader.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AdapterBatch {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private int chunkSize = 5;

    @Bean
    public Job adapterJob() throws Exception {
        return jobBuilderFactory.get("adapter")
                .incrementer(new RunIdIncrementer())
                .start(adapterStep())
                .build();
    }

    @Bean
    public Step adapterStep() throws Exception {
        return stepBuilderFactory.get("adapter")
                .<Object, Object>chunk(chunkSize)
                .reader(adapterItemReader())
                .writer(new ItemWriter() {
                    @Override
                    public void write(List list) throws Exception {
                        System.out.println("list = " + list);
                    }
                })
                .build();
    }

    @Bean
    public ItemReaderAdapter<Object> adapterItemReader() {
        ItemReaderAdapter<Object> reader = new ItemReaderAdapter<>();
        reader.setTargetMethod((String) customService());
        reader.setTargetMethod("customRead()");
        return reader;
    }

    @Bean
    public Object customService() {
        return new CustomService();
    }
}
