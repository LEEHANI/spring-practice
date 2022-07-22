package com.example.springbatch.job.chunk;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class CustomerItemWriter implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> list) throws Exception {
        list.forEach(item -> System.out.println("item = " + item));
    }
}
