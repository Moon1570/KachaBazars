/*
 * This Model creates and destroys the connection to the database and the sessions 
 * 
 */
package dao;

import java.io.File;
import java.sql.SQLException;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Connection {

	
	private SessionFactory sessionFactory = null;
	

    // Creates a new session factory and returns it
	public SessionFactory getSessionFactory() {
		
		if (sessionFactory == null) {
            // loads configuration and mappings
            Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
             
            // builds a session factory from the service registry
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);           
        }
         
        return sessionFactory;
	}

    // Closes the session factory
	public void closeSessionFactory() { 
		
		   SessionFactory sessionFactory = getSessionFactory();
		   sessionFactory.close();
		}
}
