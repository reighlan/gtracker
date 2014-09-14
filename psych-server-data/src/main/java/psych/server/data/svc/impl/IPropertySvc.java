package psych.server.data.svc.impl;

import java.util.List;
import java.util.Map;

import psych.server.data.dao.dto.PropertyCategoryDTO;
import psych.server.data.dao.dto.PropertyDTO;

/**
 * Interface to handle the property service
 * @version 2.0
 *
 * @author Toby Boyd
 */
public interface IPropertySvc {

    /**
     * Get all of the properties for the given category
     * 
     * @param propertyCategory
     *            Category to get properties for
     * @return Map<String,PropertyDTO> objects
     * @throws Exception
     *             Thrown if properties cannot be accessed
     */
    public Map<String, PropertyDTO> getPropertyMapByCategoryId(String propertyCategory)
            throws Exception;
    
	public PropertyDTO getProperty(String propertyCategory, String property) throws Exception;
    
	/**
	 * Get a list of the categories in the system
	 * 
	 * @return List of PropertyCategoryDTO objects
	 * @throws Exception
	 *             Thrown if there is a data access exception
	 */
	public List<PropertyCategoryDTO> getListOfPropertyCategories() throws Exception;

	/**
	 * Get all of the properties for the given category
	 * 
	 * @param propertyCategory
	 *            Category to get properties for
	 * @return List of propertyDTO objects
	 * @throws Exception
	 *             Thrown if there is a data access exception
	 */
	public List<PropertyDTO> getPropertyListByCategoryId(String propertyCategory) throws Exception;

	/**
	 * Adds a new category
	 * 
	 * @param propertyCategoryDTO
	 *            Category to add
	 * @return PropertyCategoryDTO to save
	 * @throws Exception
	 *             Thrown if there is a data access exception
	 */
	public PropertyCategoryDTO addCategory(PropertyCategoryDTO propertyCategoryDTO) throws Exception;

	/**
	 * Updates a property
	 * 
	 * @param propertyDTO
	 *            Property to update
	 * @throws Exception
	 *             Thrown if there is a data access exception
	 */
	public void updateProperty(PropertyDTO propertyDTO) throws Exception;

	/**
	 * Saves a property
	 * 
	 * @param propertyDTO
	 *            Property to save
	 * @throws Exception
	 *             Thrown if there is a data access exception
	 */
	public void addProperty(PropertyDTO propertyDTO) throws Exception;

}
