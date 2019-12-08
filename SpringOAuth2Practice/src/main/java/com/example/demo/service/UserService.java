package com.example.demo.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService  implements UserDetailsService
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> findAll()
	{
		return userRepository.findAll();
	}
	
	public User save(User user)
	{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public void delete(Long id)
	{
		userRepository.deleteById(id);
	}
	
	@PostConstruct
	public void init()
	{
		User may = userRepository.findByUsername("may");
		
		if(may == null)
		{
			User uuser = this.save(User.builder().username("may").password("pass").build());
			System.out.println(uuser);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		User user = userRepository.findByUsername(username);
		
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities());
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}
	
}
