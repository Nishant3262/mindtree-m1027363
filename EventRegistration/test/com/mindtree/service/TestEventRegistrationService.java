package com.mindtree.service;


import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mindtree.exception.ServiceException;
import com.mindtree.model.Employee;
import com.mindtree.model.Event;
import com.mindtree.service.impl.EventRegistrationServiceImpl;
import com.mindtree.util.ConnectionFactoryForTest;

public class TestEventRegistrationService {

	private static EventRegistrationService service;
	
	@BeforeClass
	public static void setUp()
	{
		//Need to change sessionfactory, to point different DB
		service = new EventRegistrationServiceImpl( ConnectionFactoryForTest.getInstance().getSessionFactory());
		dataBeforeTest();
	}
	
	private static void dataBeforeTest()
	{
		SessionFactory sessionFactory = ConnectionFactoryForTest.getInstance().getSessionFactory();
		Session session =sessionFactory.openSession();
		
		Transaction transation =session.beginTransaction();
		Employee emp = new Employee();
		emp.setmID("Test1");
		session.save(emp);
		
		Event event = new Event();
		event.setDescription("TEST_DESC");
		event.setEventTitle("TEST_TITLE");
		session.save(event);
		
		Employee emp2 = new Employee();
		emp2.setmID("Test2");
		session.save(emp2);
		
		Event event2 = new Event();
		event2.setDescription("NEW_DES2");
		event2.setEventTitle("NEW_TITLE2");
		session.save(event2);
		
		transation.commit();
	}
	
	@Test
	public void addEvent()
	{
		try 
		{
			service.addNewEven("NEW_TITLE", "NEW_DESCRIPTION");
		} 
		catch (ServiceException e) 
		{
			Assert.fail("Should not throw any exception");
		}
	}
	
	@Test 
	public void testAddNewEmployee()
	{
		try 
		{
			service.addNewEmployee("M1000001", "TEST", "TEST@TEST.com", "2015-09-09");
		} 
		catch (ServiceException e) 
		{
			Assert.fail("Should not throw any exception");
		}
	}
	
	@Test
	public void getAllEmployee()
	{
		Set<Employee> employees = null;
		try
		{
			employees=service.getAllEmployee();
			System.out.println("Employees :"+employees);
		} 
		catch (ServiceException e)
		{
			Assert.fail("Should not throw any exception");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRegisterEmployeeToEvent()
	{
		try 
		{
			service.registerEmployeeToEvent("Test1", "TEST_TITLE", -1);
		} 
		catch (ServiceException e) 
		{
			Assert.fail("Should not throw any exception");
		}
	}
	
	@Test
	public void testRegisterEmployeeToEventById()
	{
		try 
		{
			service.registerEmployeeToEvent("Test1", "", 2);
		} 
		catch (ServiceException e) 
		{
			Assert.fail("Should not throw any exception");
		}
	}
	@AfterClass
	public static void tearDown()
	{
		SessionFactory sessionFactory = ConnectionFactoryForTest.getInstance().getSessionFactory();
		Session session =sessionFactory.openSession();
		session.flush();
		Transaction transation =session.beginTransaction();
		Query empQuery =session.createQuery("from Employee");
		Query eventQuery =session.createQuery("from Event");
		Query query =session.createQuery("delete from Employee");
		Query query1 = session.createQuery("delete from Event");
		List<Employee> emps = empQuery.list();
		for(Employee emp : emps)
		{
			emp.setEvents(null);
		}
		
		List<Event> events = eventQuery.list();
		for(Event eve : events)
		{
			eve.setEmployees(null);
		}
		
		for(Employee emp : emps)
		{
			session.save(emp);
		}
		for(Event eve : events)
		{
			session.save(eve);
		}
		session.flush();
		
		query.executeUpdate();
		query1.executeUpdate();
		transation.commit();
	}
}
