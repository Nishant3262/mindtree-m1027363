package com.mindtree.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class ConnectionFactory {

	private SessionFactory sessionFactory;
	private static ConnectionFactory connection; 
	
	//Implementing Singleton concept
	private ConnectionFactory()
	{
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
			srBuilder.applySettings(configuration.getProperties());
			ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	public static ConnectionFactory getInstance()
	{
		if(connection==null)
		{
			connection=new ConnectionFactory();
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
