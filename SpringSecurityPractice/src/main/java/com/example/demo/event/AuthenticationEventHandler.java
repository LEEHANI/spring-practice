package com.example.demo.event;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * 
 * https://docs.spring.io/spring-security/site/docs/5.0.13.RELEASE/api/
 * @author may
 *
 */
@Component
public class AuthenticationEventHandler 
{
	@EventListener
	public void handleBadCredential(AuthenticationFailureBadCredentialsEvent event)
	{
		System.out.println(event.getAuthentication().getName() + "님 비번이 틀렸습니다.");
	}
	
	@EventListener
	public void handleBadCredential(AuthenticationSuccessEvent event)
	{
		System.out.println(event.getAuthentication().getName() + " 님 로그인 성공 !! ");
	}
}
