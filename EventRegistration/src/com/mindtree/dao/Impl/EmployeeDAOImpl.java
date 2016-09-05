package com.mindtree.dao.Impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.mindtree.dao.EmployeeDAO;
import com.mindtree.exception.FetchException;
import com.mindtree.exception.PersistanceException;
import com.mindtree.model.Employee;

public class EmployeeDAOImpl extends GenericDAOImpl<Employee, String> implements EmployeeDAO
{

	@Override
	public List<Employee> getAllEmployees(Session session) throws FetchException
	{
		String query = "FROM Employee";
		List<Employee> employees ;
		try
		{
			employees = (List<Employee>) getListByQuery(query, session, null);
		}
		catch(HibernateException e)
		{
			throw new FetchException(e.getMessage());
		}
		return employees;
	}

	@Override
	public Employee getEmployeeByMID(String mid, Session session) throws FetchException 
	{
		Employee employee = null;
		
		try
		{
			employee = getEntity(Employee.class, mid, session);
		}
		catch(HibernateException e)
		{
			throw new FetchException(e.getMessage());
		}
		return employee;
	}

	@Override
	public void insertNewEmployee(Employee emp, Session session) throws PersistanceException 
	{
		try
		{
			insertEntity(emp, session);
		}
		catch(HibernateException e)
		{
			throw new PersistanceException(e.getMessage());
		}
	}
	
}
