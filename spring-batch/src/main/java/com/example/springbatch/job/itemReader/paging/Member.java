package com.example.springbatch.job.itemReader.paging;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
public class Customer {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @OneToOne(mappedBy = "customer")
    private Address address;
}
