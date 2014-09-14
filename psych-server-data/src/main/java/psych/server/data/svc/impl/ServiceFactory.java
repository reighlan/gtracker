package psych.server.data.svc.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Utility for getting services objects
 * 
 * @version 2.0
 * 
 * @author Toby Boyd
 */
public class ServiceFactory {

	private static ApplicationContext context = null;
	private static final Log log = LogFactory.getLog(ServiceFactory.class);


	/**
     * Retrieve the service from the dependency injection framework with the
     * specified name.
     * 
     * @param name
     *            the name of the service to retrieve
     * @param type
     *            the type of the object to be returned
     * @param <T>
     *            type of object returned
     * @return the retrieved service, will be casted to the specified type
     */
    public static <T extends Object> T getService(String name, Class<T> type) {
        log.trace("getService(String,T)");
        if (context == null) {
            loadConfig();
        }
        return context.getBean(name, type);
    }

    /**
     * Retrieve the service from the dependency injection framework with the
     * specified type.
     * 
     * @param type
     *            the type of the object to be returned
     * @param <T>
     *            type of object returned
     * @return the retrieved service, will be casted to the specified type
     */
    public static <T extends Object> List<T> getServices(Class<T> type) {
        log.trace("getServices(Class<T>)");
        if (context == null) {
            loadConfig();
        }
        List<T> services = new ArrayList<T>();
        String[] names = context.getBeanNamesForType(type);
        for (String name : names) {
            services.add(context.getBean(name, type));
        }
        return services;
    }

    /**
     * Get a service using the bean name
     * 
     * @param serviceName
     *            Service to locate and return
     * @return Object that represents the service requested
     */
    public static Object getService(String serviceName) {
        log.trace("getService");
        if (context == null) {
            loadConfig();
        }
        log.debug("Extract the following service from spring:" + serviceName);
        return context.getBean(serviceName);
    }

    /**
     * Loads the spring config object, synchronized to avoid thread issues.
     * Callers of this method should do a null check on context before calling
     * this to improve performance slightly.
     */
    protected synchronized static void loadConfig() {
        log.trace("loadConfig");
        if (context == null) {
            log.debug("Retrieving application context");
            context = new ClassPathXmlApplicationContext("common-bean.xml");
        }
    }

	
}
