package com.example.springbatch.job.itemReader.page;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private String location;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
