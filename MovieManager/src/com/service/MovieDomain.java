package com.service;

import java.util.List;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.dao.MovieDAO;
import com.model.Movies;

public class MovieDomain {

	public MovieDomain()
	{
		setup();
		dao=new MovieDAO();
	}
	
	SessionFactory sessionFactory;
	
	MovieDAO dao;
	public void setup()
	{
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
		srBuilder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	public void insertNewMovie (String title, String director, String synopsis)
	{
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Movies movie = new Movies();
		movie.setTitle(title.toUpperCase());
		movie.setDirector(director.toUpperCase());
		movie.setSynopsis(synopsis);
		
		dao.save(movie, session);
		
		tx.commit();
		session.close();
	}
	
	public void updateExistingRecord( String existingTitle, String updatedTitle, String updatedDirector, String updatedSynopsis )
	{
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Movies movie = findRecord(existingTitle);
		
		if(movie != null)
		{
			movie.setTitle(updatedTitle);
			movie.setDirector(updatedDirector);
			movie.setSynopsis(updatedSynopsis);
			
			dao.update(movie, session);
		}
		else
		{
			System.out.println(" Can not update --- Multiple result found --- pleas try with above entries ");
		}
		
		tx.commit();
		session.close();
	}
	
	//Return Movie object , if unique result
	public Movies findRecord( String title )
	{
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Movies movie = null;
		
		List<Movies> movies=dao.findBySimilarTitle(title.toUpperCase(), session);
		
		if(movies.size()>1)
		{
			System.out.println(" ---- are you searching for ---- ");
			for(Movies mov:movies)
			{
				System.out.println("Titel : "+mov.getTitle() + "### Director : "+mov.getDirector()+"### Synopsis : "+mov.getSynopsis());
			}
			System.out.println(" ---- --------------------- ---- ");
		}
		else
		{
			movie=movies.get(0);
		}
		tx.commit();
		
		session.close();
		return movie;
	}
	
	public void deleteExistingRecord( String title )
	{
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Movies movie = findRecord(title);
		
		if(movie != null)
		{
			dao.delete(movie, session);
		}
		else
		{
			System.out.println(" Can not DELETE --- Multiple result found --- pleas try with above entries ");
		}
		
		tx.commit();
		session.close();
	}
	
	public Movies findRecordWithExactTitle( String title )
	{
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Movies movie = dao.findByTitle(title.toUpperCase(), session);
		
		tx.commit();
		
		session.close();
		return movie;
	}
}
