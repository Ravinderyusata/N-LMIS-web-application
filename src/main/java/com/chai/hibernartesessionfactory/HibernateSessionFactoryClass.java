package com.chai.hibernartesessionfactory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryClass {
	//Annotation based configuration
	 private static String dbConfigXMLNameWithExtensionMYSQL = "hibernate.cfg.xml";

	 private static SessionFactory buildSessionFactory(String dbConfigXMLNameWithExtension) {
	  try {
	   System.out.println("Hibernate Configuration load");
	   return new Configuration().configure(dbConfigXMLNameWithExtension).buildSessionFactory();
	  } catch (Throwable ex) {
	   System.err.println(".Initial SessionFactory creation failed" + ex);
	   throw new ExceptionInInitializerError(ex);
	  }
	 }

	 private static final SessionFactory sessionFactoryMSSQL = buildSessionFactory(dbConfigXMLNameWithExtensionMYSQL);

	 public static SessionFactory getSessionAnnotationFactory() {
	  System.out.println("-- HibernateSessionFactoryClass.getSessionFactory() method called --");
	  return sessionFactoryMSSQL;
	 }
	
}
