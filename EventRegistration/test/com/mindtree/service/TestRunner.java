package com.mindtree.service;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

	//Create DB, mentioned in hibernate.cfg.test.xml before test
	public static void main (String[] args)
	{
		Result result = JUnitCore.runClasses(TestEventRegistrationService.class);
		
	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
			
	      System.out.println(result.wasSuccessful());
	      System.exit(0);
	}
}
