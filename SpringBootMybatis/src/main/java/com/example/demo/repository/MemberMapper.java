package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Member;

@Mapper
//@Repository
public interface MemberMapper 
{
	public void insertMember(Member member);
	public List<Member> getAll();
}
