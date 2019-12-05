package com.example.demo.di;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 
 * 스프링이란 loC와 AOP를 지원하는 경량의 컨테이너 프레임워크이다
 * 대신 관리해준다(loC)와 대신넣어준다(DI)는 뜻이다. 
 * 대신해주는 것은 미리 찜해놓은 객체를 생성하고 관계를 설정시켜주고 소멸시키는 것이다.
 * 
 * BeanFactory : 빈들을 관리해주는 loc 
 * ApplicationContext : BeanFactory외에 추가적으로 다른일도 해줌
 * - 빈을 만들고 엮어주며 제공해준다.  
 * 
 * 낮은 결합도와 높은 응집도를 유지하자!
 *
 */
@Component // 의존성 주입을 받는 객체도 Bean으로 등록
public class PencilCase {
	
	public PencilCase()
	{
		System.out.println("PencilCase 생성자");
		this.mildeLiner = new MildLiner();
	}
	
	/**
	 * loC/DI가 적용되지 않은 경우, 강한 결합 
	 */
	private MildLiner mildeLiner;
	
	public String highlighterColor()
	{
		return mildeLiner.color();
	}
	
	
	/**
	 * loC/DI가 적용된 경우
	 * 
	 * DI의 세가지 유형
	 * 
	 * 1. 생성자를 이용한 의존성 삽입
	 * 2. setter()메소드를 이용한 의존성 삽입
	 * 3. 멤버 변수 삽입
	 */
	@Autowired
	@Qualifier("monoEraser")
	private Eraser eraser;
	
	public String eraserName()
	{
		return eraser.name();
	}
	
	@PostConstruct
	void init()
	{
		System.out.println(eraser);
		System.out.println("PencilCase init");
	}
	
	@PreDestroy
	void destroy()
	{
		System.out.println("PencilCase destroy");
	}
}
