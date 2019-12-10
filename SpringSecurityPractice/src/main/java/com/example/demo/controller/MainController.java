package com.example.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController 
{
	@GetMapping(value = "/")
	public String init()
	{
//		System.out.println("principal " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//		System.out.println("principal " + SecurityContextHolder.getContext().getAuthentication().getName());
//		System.out.println("principal " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		return "hello world";
	}
	
	@GetMapping(value = "/user")
	public String user()
	{
		return "user test";
	}
	
	@GetMapping(value = "/admin")
	public String admin()
	{
		return "admin test";
	}
	
	@GetMapping(value = "/success")
	public String loginSuccess()
	{
	 	return "Welcome!";
	}
	
	@GetMapping(value = "/test")
	public String test()
	{
		return "test";
	}
}
