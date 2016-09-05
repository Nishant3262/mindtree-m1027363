package com.mindtree.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

public interface GenericDAO<T,ID> {
	void insertEntity(T entity,Session session);
	T getEntity(Class<?> clazz, ID id,Session session);
	public List<T> getListByQuery(String query, Session session,Map<String,String> parameters);
	T getUniqueResultByQuery(String query,Session session);
}
