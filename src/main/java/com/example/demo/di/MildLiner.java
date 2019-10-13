package com.example.demo.di;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

@Component
public class MildLiner {
	
	public MildLiner() 
	{
		System.out.println("MildLiner 생성자");
	}
	
	String color()
	{
		return "blue";
	}
	
	@PostConstruct
	public void init()
	{
		System.out.println("mildLiner init");
	}
	
	@PreDestroy
	public void destroy()
	{
		System.out.println("mildLiner destroy");
	}
}
