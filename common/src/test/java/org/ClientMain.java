package org;

import org.impl.GreetingImpl;
import org.impl.GreetingProxy;
import org.inteface.Greeting;

public class ClientMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//sysHello1();
		//sysHello2();
		//sysHello3();
		sysHello4();
	}
	
	public static void sysHello1(){
		new GreetingImpl().sayHello("hello");
	}

	public static void sysHello2(){
		new GreetingProxy(new GreetingImpl()).sayHello("hello");
	}
	
	public static void sysHello3(){
		Greeting greeting = new JdbDynamicProxy(new GreetingImpl()).getProxy();
		greeting.sayHello("hello");
	}
	
	public static void sysHello4(){
		Greeting greeting = CGLibDynamicProxy.getInstance().getProxy(new GreetingImpl().getClass());
		greeting.sayHello("hello");
	}
}
