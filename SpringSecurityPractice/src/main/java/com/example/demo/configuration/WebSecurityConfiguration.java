package com.example.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(securedEnabled = true) // 서비스 계층의 메소드에도 보안을 추가할 수 있음
@Configuration
public class WebSecurityConfiguration 
{

}
