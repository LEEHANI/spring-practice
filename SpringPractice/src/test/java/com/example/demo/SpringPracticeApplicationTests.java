package com.example.demo;

import com.example.demo.di.PencilCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;


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
