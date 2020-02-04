package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
//@ToString
@Entity
@Alias("member")
public class Member {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long seq;
	
	private String id;
	
	@Column
	private String password;
}
