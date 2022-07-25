package com.example.springbatch.job.itemReader.page;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @OneToOne(mappedBy = "member")
    private Address address;
}
