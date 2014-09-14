package psych.server.data.svc.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import psych.server.data.dao.IPropertyDAO;
import psych.server.data.dao.dto.PropertyCategoryDTO;
import psych.server.data.dao.dto.PropertyDTO;
import psych.server.data.dao.impl.PropertyDAO;

/**
 * Property service, for allowing the getting of properties
 * 
 * @version 2.0
 * 
 * @author tboyd
 */
public class PropertySvc extends BaseSvc implements IPropertySvc {

	private static final Log log = LogFactory.getLog(PropertySvc.class);

	/**
	 * {@inheritDoc}
	 */
	public Map<String, PropertyDTO> getPropertyMapByCategoryId(String propertyCategory) throws Exception {
		log.trace("getPropertyMapByCategoryId");
		Session session = null;
		try {
			session = getSessionFactory().getCurrentSession();
			session.beginTransaction();
			IPropertyDAO propertyDAO = new PropertyDAO(session);
			Map<String, PropertyDTO> propertyMap = propertyDAO.getPropertyMapByCategoryId(propertyCategory);
			session.getTransaction().commit();
			return propertyMap;
		} catch (HibernateException ex) {
			log.error("Unable to get properties for category:" + propertyCategory + ":", ex);
			throw new Exception(ex);
		} finally {
			closeSession(session);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public PropertyDTO getProperty(String propertyCategory, String property) throws Exception {
		log.trace("getProperty");
		Session session = null;
		try {
			session = getSessionFactory().getCurrentSession();
			session.beginTransaction();
			IPropertyDAO propertyDAO = new PropertyDAO(session);
			Map<String, PropertyDTO> propertyMap = propertyDAO.getPropertyMapByCategoryId(propertyCategory);
			session.getTransaction().commit();
			return propertyMap.get(property);
		} catch (HibernateException ex) {
			log.error("Unable to get property:" + propertyCategory + ":" + property, ex);
			throw new Exception(ex);
		} finally {
			closeSession(session);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<PropertyDTO> getPropertyListByCategoryId(String propertyCategory) throws Exception {
		log.trace("getPropertyListByCategoryId");

		Session session = null;
		try {
			session = getSessionFactory().getCurrentSession();
			session.beginTransaction();
			IPropertyDAO propertyDAO = new PropertyDAO(session);
			List<PropertyDTO> propertyList = propertyDAO.getPropertyListByCategoryId(propertyCategory);
			session.getTransaction().commit();
			return propertyList;
		} catch (HibernateException ex) {
			log.error("Unable to get properties for category:" + propertyCategory + ":", ex);
			throw new Exception(ex);
		} finally {
			closeSession(session);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<PropertyCategoryDTO> getListOfPropertyCategories() throws Exception {
		log.trace("getListOfPropertyCategories");
		Session session = null;
		try {
			session = getSessionFactory().getCurrentSession();
			session.beginTransaction();
			IPropertyDAO propertyDAO = new PropertyDAO(session);
			List<PropertyCategoryDTO> propertyCatList = propertyDAO.getListOfPropertyCategories();
			session.getTransaction().commit();
			return propertyCatList;
		} catch (HibernateException ex) {
			log.error("Unable to get list of property categories", ex);
			throw new Exception(ex);
		} finally {
			closeSession(session);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public PropertyCategoryDTO addCategory(PropertyCategoryDTO propertyCategoryDTO) throws Exception {
		log.trace("addCategory");
		Session session = null;
		try {
			session = getSessionFactory().getCurrentSession();
			session.beginTransaction();
			IPropertyDAO propertyDAO = new PropertyDAO(session);
			PropertyCategoryDTO propertyCatReturn = propertyDAO.addCategory(propertyCategoryDTO);
			session.getTransaction().commit();
			return propertyCatReturn;
		} catch (HibernateException ex) {
			log.error("Unable to save property category", ex);
			throw new Exception(ex);
		} finally {
			closeSession(session);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateProperty(PropertyDTO propertyDTO) throws Exception {
		log.trace("updateProperty");
		Session session = null;
		try {
			session = getSessionFactory().getCurrentSession();
			session.beginTransaction();
			IPropertyDAO propertyDAO = new PropertyDAO(session);
			propertyDAO.updateProperty(propertyDTO);
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			log.error("Unable to update property", ex);
			throw new Exception(ex);
		} finally {
			closeSession(session);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void addProperty(PropertyDTO propertyDTO) throws Exception {
		log.trace("addProperty");
		Session session = null;
		try {
			session = getSessionFactory().getCurrentSession();
			session.beginTransaction();
			IPropertyDAO propertyDAO = new PropertyDAO(session);
			propertyDAO.addProperty(propertyDTO);
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			log.error("Unable to add property", ex);
			throw new Exception(ex);
		} finally {
			closeSession(session);
		}
	}

}
