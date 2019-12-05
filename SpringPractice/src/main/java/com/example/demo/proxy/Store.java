package com.example.demo.proxy;

public class Store 
{
	Payment payment;
	
	public Store(Payment payment)
	{
		this.payment = payment;
	}
	public void buySomething(int amount)
	{
		payment.pay(amount);
	}
}
