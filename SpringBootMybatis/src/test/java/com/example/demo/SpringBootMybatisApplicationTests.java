package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberMapper;

@SpringBootTest
class SpringBootMybatisApplicationTests {

	@Autowired
	private MemberMapper memberMapepr;
	
	@Test
	void contextLoads() {
		memberMapepr.insertMember(Member.builder().id("may").password("pass").build());
	}

}
