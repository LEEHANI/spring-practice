package com.example.demo.di;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

public interface Eraser {
	
	String name();
	
	@PostConstruct
	default void init()
	{
		System.out.println("erser init");
	}
	
	@PreDestroy
	default void destroy()
	{
		System.out.println("erser destroy");
	}
}
 
/**
 * Component 어노테이션이 붙은 클래스는 스프링 컨테이너가 알아서 Spring Bean 객체로 등록하고 생성
 * Bean은 기본적으로 등록된 Container에 1개의 인스턴스만 존재한다. 이걸 Singleton Scope라고 한다.
 * 스프링에서 빈을 싱글톤으로 만드는 이유는 여러 요청이 있을 때마다 새로 만들어서 사용하면 서버 부하가걸린다. 그래서 스프링은 각각의 빈들을 싱글톤으로 관리하고, 여러 스레드에서 이를 공유하여 사용할 수 있게 해주기 위해서.   
 */
@Component("ainEraser") // ainEraser 이름으로 Bean 등록
class AinEraser implements Eraser {
	
	@Override
	public String name() {
		return "아인 지우개";
	}
	
	@PostConstruct
	public void init()
	{
		System.out.println("ain init");
	}
	
	@PreDestroy
	public void destroy()
	{
		System.out.println("ain destroy");
	}
}

@Component("tombowEraser") // tombowEraser 이름으로 Bean 등록
class TombowEraser implements Eraser {
	
	@Override
	public String name() {
		return "톰보우 지우개";
	}
	
	@PostConstruct
	public void init()
	{
		System.out.println("tombow init");
	}
	
	@PreDestroy
	public void destroy()
	{
		System.out.println("tombow destroy");
	}
}

@Component("monoEraser") // monoEraser 이름으로 Bean 등록
class MonoEraser implements Eraser {
	
	@Override
	public String name() {
		return "모노 지우개";
	}
	
	public MonoEraser() {
		System.out.println("MonoEraser 생성자");
	}
	
	@PostConstruct
	public void init()
	{
		System.out.println("mono init");
	}
	
	@PreDestroy
	public void destroy()
	{
		System.out.println("mono destroy");
	}
}

@Component("morningGloryEraser") // morningGlroyEraser 이름으로 Bean 등록
class MorningGloryEraser implements Eraser {
	
	@Override
	public String name() {
		return "모닝글로리 지우개";
	}
	
	@PostConstruct
	public void init()
	{
		System.out.println("morningGlory init");
	}
	
	@PreDestroy
	public void destroy()
	{
		System.out.println("morningGlory destroy");
	}
}