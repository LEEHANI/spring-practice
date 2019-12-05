package com.example.demo;

import org.junit.Test;

import com.example.demo.proxy.CashPerf;
import com.example.demo.proxy.Payment;
import com.example.demo.proxy.Store;


public class StoreTest {
	@Test
	public void testPay()
	{
		Payment cashPerf = new CashPerf();
		Store store = new Store(cashPerf);
		store.buySomething(100);
	}
	
	
	@Test
	public void test()
	{   
	    long startTime = System.currentTimeMillis();
		
	    long total = 0;
	      for (int i = 0; i < 10000000; i++) {
	         total += i;
	      }
		
	    long stopTime = System.currentTimeMillis();
	    System.out.println(stopTime-startTime);
	}
}
