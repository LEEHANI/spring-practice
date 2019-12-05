package com.example.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.di.PencilCase;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringPracticeApplicationTests {

	@Autowired
	private PencilCase pencilCase;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void diTest()
	{
		assertEquals(pencilCase.eraserName(), "모노 지우개");
	}
}
