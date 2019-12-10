package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
public class UserController 
{
	@Autowired
	private UserService userService;
	
	@GetMapping("/create")
	public User create(String username, String password)
	{
		return userService.save(User.builder().username(username).password(password).build());
	}
}
