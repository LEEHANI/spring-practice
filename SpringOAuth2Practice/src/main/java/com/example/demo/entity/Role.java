package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.example.demo.enums.Authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
//@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role extends BaseEntity 
{
	private static final long serialVersionUID = 1L;
	
	@Column
	private Authority authority;
}
