package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

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
public class UserService implements UserDetailsService
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 내가 쓰는 User를 UserDetails로 바꿔야함
		User user = userRepository.findByEmail(username);
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
				
//		UserDetails userDetilas = new UserDetails() {
//			
//			@Override
//			public boolean isEnabled() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//			
//			@Override
//			public boolean isCredentialsNonExpired() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//			
//			@Override
//			public boolean isAccountNonLocked() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//			
//			@Override
//			public boolean isAccountNonExpired() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//			
//			@Override
//			public String getUsername() {
//				return user.getEmail(); //uniq
//			}
//			
//			@Override
//			public String getPassword() {
//				return user.getPassword();
//			}
//			
//			@Override
//			public Collection<? extends GrantedAuthority> getAuthorities() {
//				 List<GrantedAuthority> authorities = new ArrayList<>();
//				 authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//				return authorities;
//			}
//		};
//		
//		return userDetilas;
	}
	
	public User save(User user)
	{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
}