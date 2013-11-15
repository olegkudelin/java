/*
* @(#)Sessions
*
* @version 1.0 24.08.2013
*
* Copyright notice
*/

package oleg.kudelin.task4.connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Sessions implements AutoCloseable {
	private static final SessionFactory sessionFactory;
	static {
		try {
			Configuration configuration = new Configuration().configure();
			ServiceRegistryBuilder builder = new ServiceRegistryBuilder();
			builder.applySettings(configuration.getProperties());
			ServiceRegistry registry = builder.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(registry);
		} catch (Throwable throwable) {
			throw new ExceptionInInitializerError(throwable);
		}
	}
	
	public static Session openSession() {
		return sessionFactory.openSession();
	}

	@Override
	public void close() throws Exception {
		sessionFactory.close();
	}
}