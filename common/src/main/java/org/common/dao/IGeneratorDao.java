package org.common.dao;

public interface IGeneratorDao<T> {
	void add(T t);
	
	void delete(T t);
	
	void update(T t);
}
