package com.example.demo.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.enums.Authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
//@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User extends BaseEntity implements UserDetails
{
	private static final long serialVersionUID = 1L;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Default
	@Column
	private boolean accountNonExpired = true;
	
	@Default
	@Column
	private boolean accountNonLocked = true;
	
	@Default
	@Column
	private boolean credentialsNonExpired = true;
	
	@Default
	@Column
	private boolean enabled = true;
	
	@Default
	@Column
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Role> roles = new HashSet<Role>();

	public void bind(Authority authority)
	{
		roles.add(Role.builder().authority(authority).build());
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		
		roles.stream().forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getAuthority().getValue())));
		
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() 
	{
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() 
	{
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() 
	{
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() 
	{
		return this.enabled;
	}
}
