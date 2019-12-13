spring-security + oauth2 + jwt 개발


1. InMemoryTokenStore 
```
@Bean
public TokenStore tokenStore()
{
	return new InMemoryTokenStore();
}

```

2. JwtTokenStore
```
@Bean
public TokenStore tokenStore()
{
	return new JwtTokenStore(accessTokenConverter());
}
	
@Bean
public JwtAccessTokenConverter accessTokenConverter()
{
	return new JwtAccessTokenConverter();
}
```


## test 




- https://jwt.io/