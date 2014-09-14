package psych.server.data.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import psych.server.data.dao.IPropertyDAO;
import psych.server.data.dao.dto.PropertyCategoryDTO;
import psych.server.data.dao.dto.PropertyDTO;

/**
 * Accesses property values in the data model
 * 
 * @version 2.0
 * 
 * @author tboyd
 */
public class PropertyDAO implements IPropertyDAO {

    private Session session;

    private static final Log log = LogFactory.getLog(PropertyDAO.class);

	/**
	 * Initialize the DAO
	 * 
	 * @param session
	 *            Hibernate session to be used by methods below
	 */
    public PropertyDAO(Session session) {
        log.trace("PropertyDAO");
        this.session = session;
    }

    /**
     * {@inheritDoc}
     */
    public List<PropertyCategoryDTO> getListOfPropertyCategories() throws HibernateException{
    	log.trace("getListOfPropertyCategories");
    	
        String hql = "from PropertyCategoryDTO pcDTO order by pcDTO.catId";

        Query query = session.createQuery(hql);
        log.debug("HQL query to execute:" + query.getQueryString());

        @SuppressWarnings("unchecked")
		List<PropertyCategoryDTO> propertyList = query.list();
        return propertyList;	
    }
    
    /**
     * {@inheritDoc}
     */
    public PropertyCategoryDTO addCategory(PropertyCategoryDTO propertyCategoryDTO){
    	log.trace("addCategory");
    	session.save(propertyCategoryDTO);
    	return propertyCategoryDTO;
    }
    
    /**
     * {@inheritDoc}
     */
    public void updateProperty(PropertyDTO propertyDTO) throws HibernateException{
    	log.trace("updateProperty");
    	session.update(propertyDTO);
    }
    
    /**
     * {@inheritDoc}
     */
    public void addProperty(PropertyDTO propertyDTO) throws HibernateException{
    	log.trace("addProperty");
    	session.save(propertyDTO);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<PropertyDTO> getPropertyListByCategoryId(String propertyCategory) throws HibernateException{
    	log.trace("getPropertyListByCategoryId");
    	
    	String hql = "from PropertyDTO props where props.propertyPK.catInfo = :catId";

        Query query = session.createQuery(hql);
        query.setString("catId", propertyCategory);
        log.debug("HQL query to execute:" + query.getQueryString());

        @SuppressWarnings("unchecked")
		List<PropertyDTO> propertyList = query.list();

        return propertyList;
    	
    }
    
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Map<String, PropertyDTO> getPropertyMapByCategoryId(String propertyCategory) throws HibernateException {
        log.trace("getPropertyListByCategoryId");

        // Map to store info
        Map<String, PropertyDTO> propertyMap = new ConcurrentHashMap<String, PropertyDTO>();

        String hql = "from PropertyDTO props where props.propertyPK.catInfo = :catId";

        Query query = session.createQuery(hql);
        query.setString("catId", propertyCategory);
        log.debug("HQL query to execute:" + query.getQueryString());

        List<PropertyDTO> propertyList = query.list();

        // Fill Map
        for (PropertyDTO property : propertyList) {
            propertyMap.put(property.getPropertyPK().getPropertyId(), property);
        }

        return propertyMap;
    }

}
