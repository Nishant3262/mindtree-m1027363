package com.mindtree.dao.Impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.mindtree.dao.EventDAO;
import com.mindtree.exception.FetchException;
import com.mindtree.exception.PersistanceException;
import com.mindtree.model.Event;

public class EventDAOImpl extends GenericDAOImpl<Event, Integer> implements EventDAO
{

	@Override
	public void insertNewEvent(Event event, Session session) throws PersistanceException
	{
		try
		{
			insertEntity(event, session);
		}
		catch(HibernateException e)
		{
			throw new PersistanceException(e.getMessage());
		}
	}
	
	@Override
	public Event getEventById(int id, Session session) throws FetchException
	{
		Event event = null;
		try
		{
			event=getEntity(Event.class, id, session);
		}
		catch(HibernateException e)
		{
			throw new FetchException(e.getMessage());
		}	
		return event;
	}
	
	@Override
	public void updateEvent (Event event, Session session) throws PersistanceException
	{
		try
		{
			//insertEntity implement saveOrUpdate
			insertEntity(event, session);
		}
		catch(HibernateException e)
		{
			throw new PersistanceException(e.getMessage());
		}
	}

	@Override
	public List<Event> getEventByTitle(String title, Session session) throws FetchException 
	{
		List<Event> events = null;
		Map<String,String> parameters = new HashMap<String,String>();
		String query = "select e from Event e where e.eventTitle like :pTitle";
		parameters.put("pTitle", "%"+title+"%");
		try
		{
			events = getListByQuery(query, session, parameters);
		}
		catch(HibernateException e)
		{
			throw new FetchException(e.getMessage());
		}	
		return events;
	}
}
