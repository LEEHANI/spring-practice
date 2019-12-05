package com.example.demo.di;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiControllerTest {
	@Autowired
	ApplicationContext applicationContext;
	
	@Test
	public void getBean()
	{
		SampleController bean = applicationContext.getBean(SampleController.class);
		
		assertThat(bean).isNotNull();
	}
}
