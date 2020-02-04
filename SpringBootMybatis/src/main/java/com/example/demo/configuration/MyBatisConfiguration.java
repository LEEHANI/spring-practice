package com.example.demo.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
	
@Configuration
@MapperScan(basePackages = "com.exmaple.demo.repository")
public class MyBatisConfiguration 
{
}
	