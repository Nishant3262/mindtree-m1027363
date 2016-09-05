package com.mindtree.servicemenu;

import java.util.Scanner;
import java.util.Set;

import com.mindtree.exception.ServiceException;
import com.mindtree.model.Employee;
import com.mindtree.service.EventRegistrationService;
import com.mindtree.service.impl.EventRegistrationServiceImpl;

public class MenuDrivenService 
{
	
	EventRegistrationService service;
	
	public static void main(String[] args)
	{
		MenuDrivenService menu = new MenuDrivenService();
		menu.service= new EventRegistrationServiceImpl();
		
		Scanner sc = new Scanner(System.in);
		
		String adminMid = null;
		String adminPwd = null;
		Boolean isAuthenticated=Boolean.FALSE;
		
		Employee admin = null;
		
		System.out.println("**************************************************************************************");
		System.out.println("Welcome to EventRegistration Application");
		System.out.println("**************************************************************************************");
		System.out.println("Please Enter Your MID");
		adminMid=sc.nextLine();
		System.out.println("Please Enter the Password");
		adminPwd=sc.nextLine();
		
		
		try 
		{
			admin = menu.service.getEmployeeByMID(adminMid);
		} 
		catch (ServiceException e)
		{
			System.out.println(e.getMessage());
		}
		
		if(admin!=null)
		{
			if(admin.getPwd()==null)
			{
				System.out.println("########################################################################");
				System.out.println("You do not have enough Privilege");
				System.out.println("########################################################################");
			}
			else
			{
				if(!admin.getPwd().equals(adminPwd))
				{
					System.out.println("########################################################################");
					System.out.println("Wronge Password Please try again");
					System.out.println("########################################################################");
				}
				else
				{
					isAuthenticated=Boolean.TRUE;
					System.out.println("**************************************************************************");
					System.out.println("Welcome "+admin.getName());
					System.out.println("**************************************************************************");
				}
			}
		}
		else
		{
			System.out.println("########################################################################");
			System.out.println("No entry for Given user");
			System.out.println("########################################################################");
		}
		
		String reply = "Y";
		if(isAuthenticated)
		{
			System.out.println("Please Select options (ex :1 or 2 ...)");
			System.out.println("Option : 1 -- Add New Event");
			System.out.println("Option : 2 -- Add New Employee");
			System.out.println("Option : 3 -- get All Employee List");
			System.out.println("Option : 4 -- Register Employee to existing Event");
			reply=sc.nextLine();
			do
			{
				switch(reply)
				{
					case "1" :
						System.out.println("Please Type Event Title name");
						String eventTitle = sc.nextLine();
						System.out.println("Please Type Description about event (Limit 250)");
						String description =sc.nextLine();
						
						menu.addNewEventCall(menu.service, eventTitle, description);
						
						break;
					case "2" :
						System.out.println("Please enter the unique MID");
						String mid=sc.nextLine();
						System.out.println("Pleae enter the Employee name");
						String name= sc.nextLine();
						System.out.println("Please enter valid email Id");
						String emailId = sc.nextLine();
						System.out.println("Please enter joining Date in formate 'YYYY-MM-DD'");
						String joinDate = sc.nextLine();
						menu.addNewEmployeeCall(menu.service, mid, name, emailId, joinDate);
						
						break;
						
					case "3" :
						
						menu.getAllEmployeeCall(menu.service);
						break;
						
					case "4" :
						System.out.println("Search to Event by Title or ID -- (Type 'ID' for ID, other entry will treated as Title)");
						String option = sc.nextLine();
						int eventId = -1;
						String evntTitle = null;
						String mID = null;
						if(option.equals("ID"))
						{
							System.out.println("Enter the EventID -- It should be Number");
							try
							{
								eventId = Integer.parseInt(sc.nextLine());
							} 
							catch (NumberFormatException e)
							{
								System.out.println("Entered Text is not Number -- Please try again");
								break;
							}
						}
						else
						{
							System.out.println("Enter the EventTitle -- If more tha one event found with same name Event description will be Displayed");
							evntTitle = sc.nextLine();
						}
						System.out.println("Enter the MID of the Employee");
						mID=sc.nextLine();
						menu.registerEmployeeToEventCall(menu.service, mID, evntTitle, eventId);
						break;
					case "Y":
						break;
					default :
						System.out.println("Choice : "+reply +" is not persent :: Please try again");
						break;
				}
				
				
				System.out.println("Do you want to continue - 'Y' or 'N'");
				reply=sc.nextLine();
				if(reply.equals("Y"))
				{
					System.out.println("Please Select options (ex :1 or 2)");
					System.out.println("Option : 1 -- Add New Event");
					System.out.println("Option : 2 -- Add New Employee");
					System.out.println("Option : 3 -- get All Employee List");
					System.out.println("Option : 4 -- Register Employee to existing Event");
					reply=sc.nextLine();
				}
				else if(!reply.equals("N"))
				{
					System.out.println("Please chose between 'Y' or 'N' - case sensitive");
				}
			}
			while (!reply.equals("N"));
		}
		isAuthenticated=Boolean.FALSE;
		//Need to close the connections
		sc.close();
		System.exit(0);
	}
	
	private void addNewEventCall(EventRegistrationService service,String eventTitle,String description)
	{
		try 
		{
			service.addNewEven(eventTitle, description);
			System.out.println("\n!!! Event added Successfully !!!\n");
		} 
		catch (ServiceException e) 
		{
			System.out.println(e.getMessage());
			System.out.println("Unique constrain -- Title description pair present in DB");
		}
	}
	
	private void addNewEmployeeCall(EventRegistrationService service,String mid,String name,String emailId,String joinDate)
	{
		try 
		{
			service.addNewEmployee(mid, name, emailId, joinDate);
			System.out.println("\n!!! Employee Record added Successfully !!!\n");
		} 
		catch (ServiceException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void getAllEmployeeCall(EventRegistrationService service)
	{
		try
		{
			Set<Employee> employees = service.getAllEmployee();
			System.out.println("****************Employee Details START********************");
			for(Employee emp : employees)
			{
				System.out.println(emp.toString());
			}
			System.out.println("****************Employee Details END********************");
		} 
		catch (ServiceException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void registerEmployeeToEventCall(EventRegistrationService service,String mid,String eventTitle,int eventId)
	{
		try 
		{
			service.registerEmployeeToEvent(mid, eventTitle, eventId);
		} 
		catch (ServiceException e) 
		{
			System.out.println(e.getMessage());
		}
	}
}
