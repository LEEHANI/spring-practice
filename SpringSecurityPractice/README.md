
# spring security
  - 자바 EE 기의 엔터프라이즈 소프트웨어 애플리케이션을 위한 포괄적인 보안 서비스들을 제공
  - 스프링 기반의 애플리케이션의 보안을 담당하는 스프링 하위 프레임워크
  - 인증(Authentication)과 권한(Authorization), Filter를 이해야한다.
    + ```인증```은 애플리케이션의 작업을 수행할 수 잇는 주체(사용자) 인지 아닌지 확인
    + ```권한```은 인증된 주체가 애플리케이션의동작을 수행할 수 있도록 허락되있는지   
      * 웹 요청 권한, 메소드 호출 및 도메인 인스턴스에 대한 접근 권한
    + 많은 ```Filter```들은 DispatcherServlet 을 호출하기 전에 거쳐간다 
    + 접근 주체(Principal) 보호된 리소스에 접근하는 대상
    + 인가(Authorize) 해당 리소스에 대해 접근 가능한 권한을 가지고 있는지 확인하는 과정(After Autentication, 인증 이후)
    
  - ```@EnableGlobalMethodSecurity(securedEnabled = true)``` 어노테이션을 사용하면 서비스 단에서도 시큐리티 사용 가능
    + ```WebSecurityConfiguration.java```, ```UserService.java``` 참고 
    
        
- 유저의 요청을 AuthenticationFilter에서 Authentication 객체로 변환해 AuthenticationManager(ProviderManager)에게 넘겨주고, AuthenticationProvider(DaoAuthenticationProvider)가 실제 인증을 한 이후에 인증이 완료되면 Authentication객체를 반환해준다.
- Authentication : 사용자 ID, 패스워드와 인증 요청 컨텍스트에 대한 정보를 가지고 있다. 인증 이후의 사용자 상세정보와 같은 UserDetails 타입 오브젝트를 포함할 수도 있다. 
- UserDetails : 이름, 이메일, 전화번호와 같은 사용자 프로파일 정보를 저장하기 위한 용도로 사용한다.


        

https://coding-start.tistory.com/153        