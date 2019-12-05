package com.example.demo.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleConfiguration 
{
	@Bean
	public SampleController sampleController()
	{
		return new SampleController();
	}
}
