package org.impl;

import org.inteface.Greeting;

public class GreetingImpl implements Greeting {
	public void sayHello(String name) {
		System.out.println(name);
	}

}
