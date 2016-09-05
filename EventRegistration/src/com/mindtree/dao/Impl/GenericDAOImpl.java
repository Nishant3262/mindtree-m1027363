package com.mindtree.dao.Impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.mindtree.dao.GenericDAO;

public class GenericDAOImpl<T,ID> implements GenericDAO<T, ID> {

	@Override
	public void insertEntity(T entity, Session session) {
		session.saveOrUpdate(entity);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getEntity(Class<?> clazz, ID id, Session session) {
		return (T) session.get(clazz, (Serializable) id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getListByQuery(String query, Session session,Map<String,String> parameters) {
		Query que = session.createQuery(query);
		if(parameters!=null)
		{
			for(String key : parameters.keySet())
			{
				que.setParameter(key, parameters.get(key));
			}
		}
		return que.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getUniqueResultByQuery(String query, Session session) {
		Query que = session.createQuery(query);
		return (T) que.uniqueResult();
	}
}
