package com.chai.hibernartesessionfactory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateSessionFactoryClass {
	//Annotation based configuration
	private static SessionFactory sessionAnnotationFactory=buildSessionAnnotationFactory();
	 private static SessionFactory buildSessionAnnotationFactory() {
	        try {
	            // Create the SessionFactory from hibernate.cfg.xml
	            Configuration configuration = new Configuration();
	            configuration.configure("hibernate.cfg.xml");
	            System.out.println("Hibernate Annotation Configuration loaded");
	             
//	            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//	            System.out.println("Hibernate Annotation serviceRegistry created");
	             
	            SessionFactory sessionFactory = configuration.buildSessionFactory();
	             
	            return sessionFactory;
	        }
	        catch (Throwable ex) {
	            // Make sure you log the exception, as it might be swallowed
	            System.err.println("Initial SessionFactory creation failed." + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    } 
	 public static SessionFactory getSessionAnnotationFactory() {
		 System.out.println("hibernate session factory class getSessionAnnotationFactory()");
	        if(sessionAnnotationFactory == null) 
	        	sessionAnnotationFactory = buildSessionAnnotationFactory();
	        return sessionAnnotationFactory;
	    }
}
