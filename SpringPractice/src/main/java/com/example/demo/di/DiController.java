package com.example.demo.di;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiController {
	
	@Autowired
	private PencilCase pencilCase;
	
	@Autowired
	@Qualifier("monoEraser")
	private Eraser eraser;
	
	public DiController()
	{
		System.out.println("DiController 생성자");
	}
	
	@RequestMapping("eraser")
	public String haveEraser()
	{
		System.out.println(eraser);
		return pencilCase.eraserName();
	}
	
	/**
	 * Bean lifeCycle 관찰
	 */
	@PostConstruct
	void init()
	{
		System.out.println("DiController init");
	}
	
	@PreDestroy
	void destroy()
	{
		System.out.println("DiController destroy");
	}
}
