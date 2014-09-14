package psych.server.data.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Settings;
import org.hibernate.engine.Mapping;

/**
 * Manage the Hibernate Session Factory
 * 
 * @version 2.0
 * 
 * @author tboyd
 */
public class HibernateUtil {

    public static final int FLUSH_THRESHOLD = 50;
    private static Map<String, SessionFactory> sessionFactories = new HashMap<String, SessionFactory>(2);
    private static final Log log = LogFactory.getLog(HibernateUtil.class);
    private static final String DEFAULT = "default";
    private static Settings settings;
    private static Mapping mapping;

    /**
     * Build the default session factory
     */
    public static void buildSessionFactory() {
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            sessionFactories.put(DEFAULT, sessionFactory);
        } catch (Throwable ex) {
            log.error("SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Build the default session factory using the provided config file
     * 
     * @param file
     *            The config file to use
     */
    public static void buildSessionFactory(String file) {
        buildSessionFactory(DEFAULT, file);
    }

    /**
     * Build the session factory
     * 
     * @param name
     *            The name of the session factory
     * @param file
     *            The config file to use
     */
    public static void buildSessionFactory(String name, String file) {
        try {
            File hbmFile = new File(file);
            Configuration cfg = new Configuration().configure(file);
            SessionFactory sessionFactory = cfg.buildSessionFactory();
            mapping = cfg.buildMapping();
            settings = cfg.buildSettings();
            sessionFactories.put(name, sessionFactory);
        } catch (Throwable ex) {
            log.error("SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }



    /**
     * Get the default session factory
     * 
     * @return The default Session Factory
     * 
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactories.get(DEFAULT);
    }

    /**
     * Get the session factory that was name using the provided name
     * 
     * @param name
     *            The name to use
     * @return The session factory that was build using the provided name
     */
    public static SessionFactory getSessionFactory(String name) {
        return sessionFactories.get(name);
    }

    public static Mapping getMapping() {
        return mapping;
    }

    public static Settings getSettings() {
        return settings;
    }

    /**
     * Close the default session factory
     */
    public static void closeSessionFactory() {
        closeSessionFactory(DEFAULT);
    }

    /**
     * Close the provided session factory
     * 
     * @param name
     *            The name of the session factory to close
     */
    public static void closeSessionFactory(String name) {
        SessionFactory sessionFactory = sessionFactories.get(name);
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    /**
     * Close the provided the session if it is open
     * 
     * @param session
     *            The session to close
     */
    public static void closeSession(Session session) {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    /**
     * Close the provided the session if it is open
     * 
     * @param session
     *            The session to close
     */
    public static void closeSession(StatelessSession session) {
        if (session != null) {
            session.close();
        }
    }

    /**
     * Flush and clear the provided session and rest the provided counter if the
     * counter equals 50 or increment the counter
     * 
     * @param session
     *            The session to flush and clear
     * @param counter
     *            The counter to rest
     */
    public static int flushAndClearSession(Session session, int counter) {
        log.trace("flushAndClearSession");

        if (counter == FLUSH_THRESHOLD) {
            log.debug("Session Flushed");
            flushAndClearSession(session);
            counter = 0;
        }
        counter++;
        return counter;
    }

    /**
     * Flush and clear the provided session
     * 
     * @param session
     *            The session to flush and clear
     */
    public static void flushAndClearSession(Session session) {
        log.trace("flushAndClearSession");

        session.flush();
        session.clear();
    }

    /**
     * Flush and the provided session and reset the provided counter if the
     * counter equals FLUSH_THRESHOLD, otherwise just increment the counter.
     * 
     * @param session
     *            The session to flush and clear
     * @param counter
     *            The counter to monitor/increment/reset
     * @return The new counter value
     */
    public static int flushSession(Session session, int counter) {
        log.trace("flushSession");

        if (counter == FLUSH_THRESHOLD) {
            log.debug("Session Flushed");
            session.flush();
            counter = 0;
        }

        return ++counter;
    }
}
