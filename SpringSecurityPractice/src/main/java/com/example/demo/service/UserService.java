package com.example.demo.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.enums.Authority;
import com.example.demo.repository.UserRepository;

//AuthenticationProvider 구현체에서 인증에 사용할 사용자 인증정보를 DB에서 가져오는 역할을 하는 클래스이다
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
		User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Not Found Email"));
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
				
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
	
	@PostConstruct
	public void init()
	{
		User may = userRepository.findByUsername("may").orElse(null);
		
		if(may == null)
		{
			User user = this.save(User.builder().username("may").password("pass").authority(Authority.USER).build());
			System.out.println(user);
		}
	}
}
