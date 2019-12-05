package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Authority 
{
	USER("ROLE_USER"),
	
	ADMIN("ROLE_ADMIN");
	
	private String value;
}
