package psych.server.data.svc.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Base service for all other services to extend. Allows for dependency
 * injection of the sessionfactory by spring
 * 
 * @version 2.0
 * 
 * @author Toby Boyd
 */
public class BaseSvc {

	private SessionFactory session;

	private static final Log log = LogFactory.getLog(BaseSvc.class);
	
	/**
	 * Constructor
	 */
	public BaseSvc() {

	}

	/**
	 * Hibernate Session factory to be used by the service
	 * 
	 * @param sessionFactory
	 *            Hibernate session factory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		session = sessionFactory;
	}

	/**
	 * Get the HibernateSessionFactory associated with the service
	 * 
	 * @return Hibernate session factory
	 */
	protected SessionFactory getSessionFactory() {
		return session;
	}

	

	protected int flushAndClearSession(Session session,int counter) {
		log.trace("flushAndClearSession");
		
		if(counter == 50){
			log.debug("Flush and Clear:" + counter);
			session.flush();
			session.clear();
			return 1;
		}
		
		return ++counter;
	}
	
	/**
	 * Close the provided the session if it is open
	 * 
	 * @param session
	 *            The session to close
	 */
	protected static void closeSession(Session session) {
		if (session != null && session.isOpen()) {
			log.debug("Close Session");
			session.close();
		}
	}

}
