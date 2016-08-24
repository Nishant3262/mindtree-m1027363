package com.dao;

import java.util.List;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.model.Movies;

public class MovieDAO {

	public void save(Movies movie, Session session)
	{
		session.save(movie);
	}
	
	public Movies findByTitle ( String title, Session session )
	{
		Query query = session.createQuery("FROM Movies m where m.title = :pTitle");
		query.setParameter("pTitle", title);
		return (Movies)query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Movies> findBySimilarTitle( String title, Session session )
	{
		Query query = session.createQuery("FROM Movies m where m.title like :pTitle");
		query.setParameter("pTitle","%"+ title+"%");
		
		return (List<Movies>)query.list();
	}
	
	public void update (Movies movie, Session session)
	{
		session.update(movie);
	}
	
	public void delete (Movies movie, Session session)
	{
		session.delete(movie);
	}
}
