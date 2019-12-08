package com.example.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter 
{
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception 
	{
		resources.resourceId("resource_id")
		.stateless(false) // token으로만 인증 확인할건지?
		;
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception 
	{
		http
			.anonymous().disable()
			.authorizeRequests()
				.antMatchers("users/**").authenticated()
				.and()
			.exceptionHandling()
				.accessDeniedHandler(new OAuth2AccessDeniedHandler()) // oauth2 error 
			;
	}
}
