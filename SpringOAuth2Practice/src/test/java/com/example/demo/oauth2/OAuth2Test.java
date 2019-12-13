package com.example.demo.oauth2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OAuth2Test 
{
	@LocalServerPort
    int port;
	
	private String accessToken;
	
	@Before
    public void setUp() 
    {
    	RestAssured.port = port;
    }
	
	@Test
	public void getAccessToken() throws Exception
	{
		RestAssured
			.given()
				.auth().preemptive().basic("may-client", "may-password")
				.contentType("application/x-www-form-urlencoded")
				.param("username", "may")
				.param("password", "pass")
				.param("grant_type", "password")
			.when()
				.post("/oauth/token")
			.then()
				.statusCode(200)
				.log().all();
	}
	
	public void getUser() throws Exception
	{
		RestAssured
			.given()
				.header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzU5NjA0MjcsInVzZXJfbmFtZSI6Im1heSIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJhMzgwMjA4Ni02ZGUzLTQ0Y2ItODYyZi1lNWFiMjk5ZjMzZGYiLCJjbGllbnRfaWQiOiJtYXktY2xpZW50Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIiwidHJ1c3QiXX0.8U8YwoxNEWjnwX0Tsa4i3xtlQZIJ8KEaU2YIk2j-YEU")
			.when()
				.get("/users/user")
			.then()
				.statusCode(200)
				.log().all();
	}
	
}
