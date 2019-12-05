package com.example.demo.proxy;

import org.springframework.util.StopWatch;

public class CashPerf implements Payment 
{
	Payment cash = new Cash();

	/**
	 * proxy pattern
	 */
	@Override
	public void pay(int amount) 
	{
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		cash.pay(amount);
		
		stopWatch.stop();
		System.out.println(stopWatch.prettyPrint());
	}
}
