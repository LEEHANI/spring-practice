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
	public User create(String email, String password)
	{
		User user = User.builder().email(email).password(password).build();
		
		return userService.save(user);
	}
}
