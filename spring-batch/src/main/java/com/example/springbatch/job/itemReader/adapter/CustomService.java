package com.example.springbatch.job.itemReader.adapter;

public class CustomService<T> {

    private int cnt = 0;

    public T customRead() {
        return (T) ("item" + cnt++);
    }

}
