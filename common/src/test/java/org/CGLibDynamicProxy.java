/**
 * 
 */
package org;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * @author Administrator
 *
 */
public class CGLibDynamicProxy implements MethodInterceptor {

	private static CGLibDynamicProxy instance = new CGLibDynamicProxy();
	
	private CGLibDynamicProxy(){
		
	}
	
	public static CGLibDynamicProxy getInstance(){
		return instance;
	}
	
	public <T> T getProxy(Class<T> cls){
		return (T)Enhancer.create(cls, this);
	}
	
	public Object intercept(Object target, Method method, Object[] arg2,
			MethodProxy proxy) throws Throwable {
		before();
		Object resutl = proxy.invoke(target, arg2);
		after();
		return resutl;
	}

	private void before(){
		System.out.println("before");
	}
	
	private void after(){
		System.out.println("after");
	}
}
