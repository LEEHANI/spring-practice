package com.example.demo.configuration;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	@Resource(name = "userService")
	private UserDetailsService userDetailsService;
	
	/**
	 * 스프링 시큐리티 룰을 무시하게 하는 url 규칙
	 */
	@Override
	public void configure(WebSecurity web) throws Exception 
	{
		web.ignoring()
			.antMatchers("/resources/**");
		
		super.configure(web);
	}
	
	/**
	 * csrf().disable() csrf 보안 설정 비활성화
	 * .anyRequest().authenticated() 어떠한 요청이든 인증해야한다. 
	 * .authorizeRequests() // 요청 권한 지정, AccessDecisionmanager에 설정되는 access정보들. 이 설정은 추후 FilterSecurityInterceptor에서 권한 인증에 사용될 정보들
	 * 
	 * [antMatchers의 항목들] 
	 * 1.  anonymous()     인증되지 않은 사용자가 접근 가능 
	 * 2.  authenticated() 인증되지 않은 사용자가 접근 가능 
	 * 3.  fullyAuthenticated() 
	 * 4.  hasRole() or hasAnyRole() 특정 권한을 가지는 사용자만 접근 
	 * 5.  hasAuthority() or hasAnyAuthority() 특정 권한을 가지는 사용자만 접근 
	 * 6.  hasIpAddress() 특정 아이피 주소를 가지는 사용자만 접근
	 * 7.  access() SpEL 표현식에 의한 결과에 따라 접근
	 * 8.  not() 접근 제한 기능을 해제
	 * 9.  permitAll() or denyAll() 접근을 전부 허용하거나 제한 
	 * 10. rememberMe() 리멤버 기능을 통해 로그인한사용자만 접근할 수 있다. 로그인 정보를 유지
	 * 
	 * 
	 * [exceptionHandling]
	 * 1. accessDeniedHandler() or accessDeniedPage() 권한 체크 실패할 때
	 * 2. authenticationEntryPoint() 현재 인증된 사용자가 없는데 인증되지 않은 사용자가 보호된 리소스에 접근했을 때
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http
			.csrf().disable()
			.httpBasic()
			.and()
			.authorizeRequests() 
				.antMatchers("/user").hasRole("USER")
				.antMatchers("/admin").hasRole("ADMIN")
				.antMatchers("/test", "/create", "/success").permitAll()
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.usernameParameter("email")
				.passwordParameter("password")
				.defaultSuccessUrl("/success")
				.permitAll()
			;
		
		super.configure(http);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	/**
	 * 인증 객체 만들기
	 * 1. inMemoryAuthentication
	 * 2. userDetailsService
	 * 
	 * auth.userDetailsService(userDetailsService)
	 * userDetailsService는 AuthenticationProvider 구현체에서 인증에 사용할 사용자 인증정보를 DB에서 가져오는 역할을 하는 클래스.  
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception 
	{
//		auth
//			.inMemoryAuthentication()
//				.withUser("user").password(passwordEncoder().encode("pass")).roles("USER")
//			.and()
//				.withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
//			;
		
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource()
	{
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
//	@Bean
//	public AccessDeniedHandler accessDeniedHandler()
//	{
//		AccessDeniedHandler handler = new AccessDeniedHandler() {
//			
//			@Override
//			public void handle(HttpServletRequest request, HttpServletResponse response,
//					AccessDeniedException accessDeniedException) throws IOException, ServletException {
//				
//				System.out.println("accessDenied !!");
//			}
//		}; 
//		
//		return handler;
//	}
	
//	@Bean
//    public AuthenticationEntryPoint authenticationEntryPoint(String loginFormUrl){
//		
//		LoginUrlAuthenticationEntryPoint entry = new LoginUrlAuthenticationEntryPoint(loginFormUrl);
//		
//        return entry;
//    }
	
}
