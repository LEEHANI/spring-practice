package com.example.demo.entity;



import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.demo.enums.Authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	@Column
	private String username;
	
	@Column
	@Setter
	private String password;
	
	@Column
	private Authority authority;
	
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		Set<GrantedAuthority> authorities = new HashSet<>();
		
		authorities.add(new SimpleGrantedAuthority(authority.getValue()));
		
		return authorities;
	}
	
}
