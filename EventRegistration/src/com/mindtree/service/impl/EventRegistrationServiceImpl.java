package com.mindtree.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.mindtree.dao.EmployeeDAO;
import com.mindtree.dao.EventDAO;
import com.mindtree.dao.Impl.EmployeeDAOImpl;
import com.mindtree.dao.Impl.EventDAOImpl;
import com.mindtree.exception.FetchException;
import com.mindtree.exception.PersistanceException;
import com.mindtree.exception.ServiceException;
import com.mindtree.model.Employee;
import com.mindtree.model.Event;
import com.mindtree.service.EventRegistrationService;
import com.mindtree.util.ConnectionFactory;


public class EventRegistrationServiceImpl implements EventRegistrationService{
 
	private SessionFactory sessionFactory;
	private EmployeeDAO employeeDAO;
	private EventDAO eventDAO;
	
	/**
	 * Constructor
	 */
	public EventRegistrationServiceImpl()
	{
		sessionFactory = ConnectionFactory.getInstance().getSessionFactory();
		employeeDAO = new EmployeeDAOImpl();
		eventDAO = new EventDAOImpl();
		addAdminDetails();
	}
	
	/**
	 * Construction with Argument
	 * @param sessionFactory
	 */
	public EventRegistrationServiceImpl( SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
		employeeDAO = new EmployeeDAOImpl();
		eventDAO = new EventDAOImpl();
		addAdminDetails();
	}
	
	/**
	 * Function for adding Admin details from properties file
	 */
	private void addAdminDetails()
	{
		ResourceBundle rb = ResourceBundle.getBundle("adminUsers");
		Enumeration<String> keys = rb.getKeys();
		while (keys.hasMoreElements())
		{
			String key = keys.nextElement();
			String value = rb.getString(key);
			
			String[] attr = value.split(",");
			//M0000001,admin,admin@mindtree,2016-06-07,Admin123
			Employee emp = new Employee();
			Date joiningDate = null;
			
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			emp.setmID(attr[0]);
			emp.setName(attr[1]);
			emp.setEmailId(attr[2]);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try 
			{
				joiningDate= sdf.parse(attr[3].trim());
				emp.setJoinDate(joiningDate);
				emp.setPwd(attr[4]);
				employeeDAO.insertNewEmployee(emp, session);
				transaction.commit();
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
			catch (PersistanceException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Function to get All Employee Details
	 */
	@Override
	public Set<Employee> getAllEmployee() throws ServiceException
	{
		Set<Employee> employees = null;
		
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		try 
		{
			List<Employee> empls =employeeDAO.getAllEmployees(session);
		
			for(Employee em : empls)
			{
				em.getEvents().size();
			}
			//To transfer properties to DO
			employees = new HashSet<Employee>(empls);
			
			transaction.commit();
		} 
		catch (FetchException e) 
		{
			transaction.rollback();
			throw new ServiceException(e.getMessage());
		}
		finally
		{
			session.close();
		}
		return employees;
	}
	
	/**
	 * Function to add new event
	 */
	@Override
	public void addNewEven(String eventTitle,String description) throws ServiceException 
	{
		Event event = new Event();
		event.setEventTitle(eventTitle);
		event.setDescription(description);
		
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		try 
		{
			eventDAO.insertNewEvent(event, session);
			transaction.commit();
		} 
		catch (PersistanceException e) 
		{
			transaction.rollback();
			throw new ServiceException(e.getMessage());
		}
		finally
		{
			session.close();
		}
	}
	
	/**
	 * Add new Employee, throws exception if employee already present
	 */
	@Override
	public void addNewEmployee(String mid, String name, String emailId, String joinDate) throws ServiceException
	{
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try 
		{
			Date joiningDate = null;
			if(joinDate!=null)
			{
				joiningDate = sdf.parse(joinDate);
			}
			//Check to see if Employee already exist
			if(employeeDAO.getEmployeeByMID(mid, session)!=null)
			{
				transaction.rollback();
				throw new ServiceException("Can't create new Employee, Given MID already in use ");
			}
			if(null!=emailId && !emailValidator(emailId))
			{
				transaction.rollback();
				throw new ServiceException("Given Email pattern not supported by our system");
			}
			else if(emailId==null)
			{
				transaction.rollback();
				throw new ServiceException("Please provide Email Id");
			}
			Employee employee = new Employee();
			employee.setmID(mid);
			if(emailId!=null)
			{
				employee.setEmailId(emailId);
			}
			if(joiningDate!=null)
			{
				employee.setJoinDate(joiningDate);
			}
			if(name!=null)
			{
				employee.setName(name);
			}
			employeeDAO.insertNewEmployee(employee, session);
			transaction.commit();
		} 
		catch (ParseException e)
		{
			transaction.rollback();
			throw new ServiceException("Given Date is not in formate YYYY-MM-DD");
		}
		catch (FetchException e) 
		{
			transaction.rollback();
			throw new ServiceException(e.getMessage());
		} 
		catch (PersistanceException e)
		{
			transaction.rollback();
			throw new ServiceException(e.getMessage());
		}
	}
	
	/**
	 * For updating existing Employee records
	 */
	@Override
	public void updateEmployee (String mid, String name, String emailId, String joinDate, String password ) throws ServiceException
	{
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try 
		{
			Date joiningDate = null;
			if(joinDate!=null)
			{
				joiningDate = sdf.parse(joinDate);
			}
			//Check to see if Employee already exist
			Employee employee=employeeDAO.getEmployeeByMID(mid, session);
			
			if(null!=emailId && !emailValidator(emailId))
			{
				transaction.rollback();
				throw new ServiceException("Given Email pattern not supported by our system");
			}
			if(employee!=null)
			{
				if(emailId!=null)
				{
					employee.setEmailId(emailId);
				}
				if(joiningDate!=null)
				{
					employee.setJoinDate(joiningDate);
				}
				if(name!=null)
				{
					employee.setName(name);
				}
				employeeDAO.insertNewEmployee(employee, session);
				transaction.commit();
			}
			else
			{
				transaction.rollback();
				throw new ServiceException("Employee do not exist");
			}
		} 
		catch (ParseException e)
		{
			transaction.rollback();
			throw new ServiceException("Given Date is not in formate YYYY-MM-DD");
		}
		catch (FetchException e) 
		{
			transaction.rollback();
			throw new ServiceException(e.getMessage());
		} 
		catch (PersistanceException e)
		{
			transaction.rollback();
			throw new ServiceException(e.getMessage());
		}
	}
	
	/**
	 * Function to validate the Email format
	 * @param emailId
	 * @return
	 */
	 private Boolean emailValidator(String emailId)
	 {
		 Boolean isValid = Boolean.FALSE;
		 
		 Pattern pattern = Pattern.compile("^[A-Za-z0-9_]+@[A-Za-z0-9_]+\\.[A-Za-z]{2,6}$");
		 Matcher match = pattern.matcher(emailId);
		 
		 isValid=match.matches();
		 
		 return isValid;
	 }
	 
	/**
	 * Function to associate Employee to existing event
	 */
	@Override
	public void registerEmployeeToEvent(String mid,String eventTitle,int eventId) throws ServiceException
	{
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Employee employee=null;
		Event event = null;
		List<Event> events = null;
		try 
		{
			employee=employeeDAO.getEmployeeByMID(mid, session);
		} 
		catch (FetchException e) 
		{
			transaction.rollback();
			throw new ServiceException(e.getMessage());
		}
		
		if(employee==null)
		{
			transaction.rollback();
			throw new ServiceException("No Employee Found with MID : "+ mid);
		}
		
		if(eventId>0)
		{
			try 
			{
				event=eventDAO.getEventById(eventId, session);
				if(event!=null)
				{
					event.getEmployees().add(employee);
					employee.getEvents().add(event);
					eventDAO.updateEvent(event, session);
				}
				else
				{
					transaction.rollback();
					throw new ServiceException("No Event exist with Id : "+ eventId);
				}
				transaction.commit();
			} 
			catch (FetchException e) 
			{
				transaction.rollback();
				throw new ServiceException("No Event exist with Id : "+ eventId);
			} 
			catch (PersistanceException e) 
			{
				transaction.rollback();
				throw new ServiceException(e.getMessage());
			}
			
		}
		else
		{
			//If user send only title, as not sure about the eventId
			try 
			{
				events = eventDAO.getEventByTitle(eventTitle, session);
				if(events != null)
				{
					if(events.size()>1)
					{
						System.out.println("#####################################################");
						System.out.println("More Than one Event fount, Try last action with given Event IDs");
						for(Event e : events)
						{
							System.out.println(e.toString());
						}
					}
					else if(events.size()==1)
					{
						event=events.get(0);
						event.getEmployees().add(employee);
						employee.getEvents().add(event);
						eventDAO.updateEvent(event, session);
						transaction.commit();
					}
					else
					{
						transaction.rollback();
						throw new ServiceException("No Event Found with eventTitle like : "+ eventTitle);
					}
				}
			} 
			catch (FetchException e) 
			{
				transaction.rollback();
				throw new ServiceException(e.getMessage());
			}
			catch (PersistanceException e) 
			{
				transaction.rollback();
				throw new ServiceException(e.getMessage());
			}
		}
	}
	
	@Override
	public Employee getEmployeeByMID(String mid) throws ServiceException 
	{
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Employee employee = null;
		try 
		{
			employee=employeeDAO.getEmployeeByMID(mid, session);
			transaction.commit();
		} 
		catch (FetchException e) 
		{
			transaction.rollback();
			throw new ServiceException(e.getMessage());
		}
		
		
		return employee;
	}
	
	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
