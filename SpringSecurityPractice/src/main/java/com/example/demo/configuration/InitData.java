package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.entity.User;
import com.example.demo.enums.Authority;
import com.example.demo.repository.UserRepository;

@Component
public class InitData implements CommandLineRunner
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception 
	{
		userRepository.save(User.builder().email("may").password(passwordEncoder.encode("may")).authority(Authority.USER).build());
		userRepository.save(User.builder().email("admin").password(passwordEncoder.encode("admin")).authority(Authority.ADMIN).build());
	}

}
