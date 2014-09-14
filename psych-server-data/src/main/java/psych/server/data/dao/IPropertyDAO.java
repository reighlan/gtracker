package psych.server.data.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import psych.server.data.dao.dto.PropertyCategoryDTO;
import psych.server.data.dao.dto.PropertyDTO;

/**
 * Interface for accessing Property Values
 * 
 * @version 2.0
 * 
 * @author tboyd
 */
public interface IPropertyDAO {

	/**
	 * Get a list of the categories in the system
	 * 
	 * @return List of PropertyCategoryDTO objects
	 * @throws HibernateException
	 *             Thrown if there is a data access exception
	 */
	public List<PropertyCategoryDTO> getListOfPropertyCategories() throws HibernateException;

	/**
	 * Get all of the properties for the given category
	 * 
	 * @param propertyCategory
	 *            Category to get properties for
	 * @return List of propertyDTO objects
	 * @throws Exception
	 *             Thrown if there is a data access exception
	 */
	public List<PropertyDTO> getPropertyListByCategoryId(String propertyCategory) throws HibernateException;

	/**
	 * Adds a new category
	 * 
	 * @param propertyCategoryDTO
	 *            Category to add
	 * @return PropertyCategoryDTO to save
	 * @throws HibernateException
	 *             Thrown if there is a data access exception
	 */
	public PropertyCategoryDTO addCategory(PropertyCategoryDTO propertyCategoryDTO) throws HibernateException;

	/**
	 * Updates a property
	 * 
	 * @param propertyDTO
	 *            Property to update
	 * @throws HibernateException
	 *             Thrown if there is a data access exception
	 */
	public void updateProperty(PropertyDTO propertyDTO) throws HibernateException;

	/**
	 * Saves a property
	 * 
	 * @param propertyDTO
	 *            Property to save
	 * @throws HibernateException
	 *             Thrown if there is a data access exception
	 */
	public void addProperty(PropertyDTO propertyDTO) throws HibernateException;

	/**
	 * Get all of the properties for the given category
	 * 
	 * @param propertyCategory
	 *            Category to get properties for
	 * @return Map<String,SlProperty> objects
	 * @throws Exception
	 *             Thrown if there is a data access exception
	 */
	public Map<String, PropertyDTO> getPropertyMapByCategoryId(String propertyCategory) throws HibernateException;

}
