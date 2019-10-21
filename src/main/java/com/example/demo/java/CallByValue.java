package com.example.demo.java;

/**
 * 자바는 call by value 인가, call by reference 인가? 
 *
 */
public class CallByValue {

	int value;
	
	public CallByValue(int value) {
		this.value = value;
	}
	
	public static void swap(CallByValue c1, CallByValue c2){
		int temp = c1.value;
		c1.value = c2.value;
		c2.value = temp;
		
		System.out.println("[메소드 내부 변수] a  : " + c1.value + ", c2 : " + c2.value);
	}
	
	public static void main(String[] args) {

		//기본 자료형 스왑
		int a = 1;
		int b = 5;
		System.out.println("[swap 실행 전 변수] a  : " + a + ", b : " + b);
		swap(a, b);
		System.out.println("[swap 실행 후  변수] a  : " + a + ", b : " + b);
		/**
		 * [swap 실행 전 변수] a  : 1, b : 5
   		 * [메소드 내부 변수] a  : 5, b : 1
		 * [swap 실행 후  변수] a  : 1, b : 5
		 * => call by value
		 */
		
		System.out.println();
		
		//래퍼 클래스 스왑
		Integer integerA = 1;
		Integer integerB = 5;
		System.out.println("[swapInteger() 실행 후  변수] integerA  : " + integerA + ", integerB : " + integerB);
		swapInteger(integerA, integerB);
		System.out.println("[swapInteger() 실행 후  변수] integerA  : " + integerA + ", integerB : " + integerB);
		/**
		 * [swapInteger 실행 전 변수] integerA  : 1, integerB : 5
   		 * [메소드 내부 변수] a  : 5, b : 1
		 * [swapInteger 실행 후  변수] integerA  : 1, integerB : 5
		 * 래퍼클래스는 primitive 값을 가리킨다. 
		 * => call by value
		 */

		System.out.println();
		
		// 객체 스왑
		CallByValue c1 = new CallByValue(10);
		CallByValue c2 = new CallByValue(20);
		System.out.println("[swap() 실행 후  변수] c1  : " + c1.value + ", c2 : " + c2.value);
		swap(c1, c2);
		System.out.println("[swap() 실행 후  변수] c1  : " + c1.value + ", c2 : " + c2.value);
		/**
		 * [swap() 실행 후  변수] c1  : 10, c2 : 20
		 * [메소드 내부 변수] a  : 20, c2 : 10
		 * [swap() 실행 후  변수] c1  : 20, c2 : 10
		 * primitive type이 아니면 call by reference 처럼 보인다.
		 * 참조자료형의 경우 해당하는 변수가 가지는 값이 레퍼런스이므로 인자로 넘길 때 Call by Value에 의해 변수가 가지고 있는 레퍼런스를 복사하여 전달한다. 
		 * 이 경우 레퍼런스를 바꾼게 아니라 단지 value의 속성 값을 바꿨을 뿐이다. ! 
		 * 
		 * => call by value
		 */
	}
	
	public static void swapInteger(Integer a, Integer b) {
		
		int temp = a;
		a = b;
		b = temp;

		System.out.println("[메소드 내부 변수] a  : " + a + ", b : " + b);
	}
	
	public static void swap(int a, int b) {
		
		int temp = a;
		a = b;
		b = temp;

		System.out.println("[메소드 내부 변수] a  : " + a + ", b : " + b);
	}
}
