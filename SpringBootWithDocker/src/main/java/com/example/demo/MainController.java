package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
	@GetMapping(value = "/")
	public String getMethodName(@RequestParam(defaultValue = "world") String param) {
		return "hello, " + param;
	}
}
