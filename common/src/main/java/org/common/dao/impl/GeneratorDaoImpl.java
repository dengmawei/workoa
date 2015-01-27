package org.common.dao.impl;

import org.common.dao.IGeneratorDao;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class GeneratorDaoImpl<T>  extends SqlSessionDaoSupport implements IGeneratorDao<T> {
	public void add(T t) {
		this.getSqlSession().insert(getStatementName("add",t), t);
	}

	public void delete(T t) {
		this.getSqlSession().delete(getStatementName("delete",t), t);
	}

	public void update(T t) {
		this.getSqlSession().update(getStatementName("update",t), t);
	}

	private String getStatementName(String prefix,T t){
		return t.getClass().getSimpleName()+"."+prefix+t.getClass().getSimpleName();
	}
}
