package org.impl;

import org.inteface.Greeting;

public class GreetingProxy implements Greeting {
	private GreetingImpl greetingImpl;
	
	public GreetingProxy(GreetingImpl greetingImpl) {
		this.greetingImpl = greetingImpl;
	}

	public void sayHello(String name) {
		before();
		greetingImpl.sayHello(name);
		after();
	}

	private void before(){
		System.out.println("before");
	}
	
	private void after(){
		System.out.println("after");
	}
}
