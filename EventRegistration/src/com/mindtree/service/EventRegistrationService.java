package com.mindtree.service;

import java.util.Set;

import org.hibernate.Session;

import com.mindtree.exception.FetchException;
import com.mindtree.exception.ServiceException;
import com.mindtree.model.Employee;

public interface EventRegistrationService {
	
	Set<Employee> getAllEmployee() throws ServiceException;
	
	void addNewEven(String eventTitle,String description) throws ServiceException;
	
	void registerEmployeeToEvent(String mid,String eventTitle,int eventId) throws ServiceException;
	
	void addNewEmployee(String mid, String name, String emailId, String joinDate) throws ServiceException;
	
	void updateEmployee (String mid, String name, String emailId, String joinDate, String password ) throws ServiceException;
	
	Employee getEmployeeByMID(String mid) throws ServiceException;
}
