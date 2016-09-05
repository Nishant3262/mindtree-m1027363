package com.mindtree.dao;

import java.util.List;

import org.hibernate.Session;

import com.mindtree.exception.FetchException;
import com.mindtree.exception.PersistanceException;
import com.mindtree.model.Event;

public interface EventDAO 
{
	void insertNewEvent(Event event,Session session) throws PersistanceException;
	
	Event getEventById(int id, Session session) throws FetchException;
	
	List<Event> getEventByTitle(String title, Session session) throws FetchException;
	
	void updateEvent (Event event, Session session) throws PersistanceException;
}
