package org;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		List<TestObject> list = new ArrayList<TestObject>();
		
		list.add(new TestObject(1, "test1", 1.1));
		list.add(new TestObject(2, "test2", 2.2));
		list.add(new TestObject(3, "test3", 3.3));
		list.add(new TestObject(4, "test4", 4.4));
		/*list.add("demgmawei");
		list.add(1);
		list.add(2.2);
		list.add(true);*/
		
		TestObject obj;
		Iterator<TestObject> iterator = list.iterator();
		while(iterator.hasNext()){
			obj = iterator.next();
			
			System.out.println(obj);
		}
 	}
}
