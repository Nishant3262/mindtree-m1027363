package com.mindtree.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class ConnectionFactoryForTest {

	private SessionFactory sessionFactory;
	private static ConnectionFactoryForTest connection; 
	
	//Implementing Singleton concept
	private ConnectionFactoryForTest()
	{
			Configuration configuration = new Configuration();
			//Pointing different DB, for test
			configuration.configure("hibernate.cfg.test.xml");
			ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
			srBuilder.applySettings(configuration.getProperties());
			ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	public static ConnectionFactoryForTest getInstance()
	{
		if(connection==null)
		{
			connection=new ConnectionFactoryForTest();
		}
		return connection;
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
