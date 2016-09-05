package com.mindtree.dao;

import java.util.List;

import org.hibernate.Session;

import com.mindtree.exception.FetchException;
import com.mindtree.exception.PersistanceException;
import com.mindtree.model.Employee;

public interface EmployeeDAO {
	
	List<Employee> getAllEmployees(Session session) throws FetchException;
	
	Employee getEmployeeByMID(String mid,Session session) throws FetchException;
	
	void insertNewEmployee(Employee emp,Session session) throws PersistanceException;
}
