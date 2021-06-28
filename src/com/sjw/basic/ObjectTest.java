package com.sjw.basic;

public class ObjectTest {

	public static void main(String[] args) {
		ClassA a = new ClassA();
		ClassB b = new ClassB();
		
	}

}

class ClassA{
	public ClassA() {
		System.out.println("a");
	}
}

class ClassB extends ClassA{
	public ClassB() {
		super();
		System.out.println("b");
	}
}