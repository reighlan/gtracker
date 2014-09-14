package psych.server.data.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import psych.server.data.dao.dto.PropertyDTO;
import psych.server.data.svc.impl.IPropertySvc;
import psych.server.data.svc.impl.ServiceFactory;
import psych.server.data.util.enums.IPropertyEnum;

/**
 * Utility assists with extracting properties from the database instead of using
 * property files. Properties are also cached in this class. It is possible to
 * clear the cache by calling: clearCache()
 * 
 * @version 2.0
 * 
 * @author Toby Boyd
 */
public class PropertyUtil {

	public static Map<String, Map<String, PropertyDTO>> categoriesMap = new ConcurrentHashMap<String, Map<String, PropertyDTO>>();
	public static final String STR_VALUE_NOT_FOUND = "Value not found:";

	private static final Log log = LogFactory.getLog(PropertyUtil.class);

	/**
	 * Method to be used for retrieval of any property
	 * 
	 * @param re
	 *            Enum that represents the property to return
	 * @return String value of the property
	 */
	public static String getPropertyValue(IPropertyEnum re) {
		log.trace("getPropertyValue");
		try {
			log.debug(re.getProperty() + ":" + re.getPropertyCategory());
			Map<String, PropertyDTO> hmCat = cacheCategory(re, false);
			// If the cache does not already exists then fetch it
			if (hmCat == null) {
				return "No Constants in category:" + re.getPropertyCategory();
			}
			// Try to pull constant from map
			PropertyDTO keyValue = hmCat.get(re.getProperty());
			if (keyValue == null) {
				log.error("keyValue not found:" + re.getProperty());
				// Try to load Category since key may have been added since last
				// try
				hmCat = cacheCategory(re, true);
				keyValue = hmCat.get(re.getProperty());
				if (keyValue == null) {
					return STR_VALUE_NOT_FOUND + re.getPropertyCategory() + ":" + re.getProperty();
				}
			}
			log.debug("Value found:" + keyValue);
			return keyValue.getPropertyValue();
		} catch (Exception ex) {
			log.error("getConstant - Error", ex);
			return "!!Error getting constant!!";
		}
	}

	/**
	 * Method to be used for retrieval of any property, NULL will be returned if
	 * the value cannot be found OR if these is any kind of exception. This
	 * means the only way to tell why it is null is to check the log file
	 * 
	 * @param re
	 *            Enum that represents the property to return
	 * @return String value of the property
	 */
	public static String getPropertyValueNull(IPropertyEnum re) {
		log.trace("getPropertyValueNull");
		try {
			return PropertyUtil.getPropertyValueException(re);
		} catch (Exception ex) {
			// Error message is logged in
			// getPropertyValueException(IPropertyEnum re)
			return null;
		}
	}

	/**
	 * Method to be used for retrieval of any property, will thrown an exception
	 * if the property is not found
	 * 
	 * @param re
	 *            Enum that represents the property to return
	 * @return String value of the property
	 * @throws Exception
	 *             Thrown if there is an exception getting the property or the
	 *             property is not found
	 */
	public static String getPropertyValueException(IPropertyEnum re) throws Exception {
		log.trace("getPropertyValueException");
		try {
			log.debug(re.getProperty() + ":" + re.getPropertyCategory());
			Map<String, PropertyDTO> hmCat = cacheCategory(re, false);
			// If the cache does not already exists then fetch it
			if (hmCat == null) {
				throw new Exception("emptyCategory:" + re.getPropertyCategory());
			}
			// Try to pull constant from map
			PropertyDTO keyValue = hmCat.get(re.getProperty());
			if (keyValue == null) {
				log.error("keyValue not found:" + re.getProperty());
				// Try to load Category since key may have been added since last
				// try
				hmCat = cacheCategory(re, true);
				keyValue = hmCat.get(re.getProperty());
				if (keyValue == null) {
					throw new Exception("propertyNotFound:" + re.getPropertyCategory() + "." + re.getProperty());
				}
			}
			log.debug("Value found:" + keyValue.getPropertyValue());
			return keyValue.getPropertyValue();
		} catch (Exception ex) {
			log.error("Unable to get property value due to backend exception", ex);
			throw new Exception("generalError", ex);
		}
	}

	


	/**
	 * Method to be used for retrieval of any property and decrypt it, will thrown an exception
	 * if the property is not found or cannot be decrypted
	 * 
	 * @param re
	 *            Enum that represents the property to return
	 * @return String value of the property
	 * @throws Exception
	 *             Thrown if there is an exception getting the property or the
	 *             property is not found or the decrypt fails
	 */
	public static String getPropertyValueDecryptException(IPropertyEnum re) throws Exception {
		log.trace("getPropertyValueDecryptException");
		try {
			log.debug(re.getProperty() + ":" + re.getPropertyCategory());
			Map<String, PropertyDTO> hmCat = cacheCategory(re, false);
			// If the cache does not already exists then fetch it
			if (hmCat == null) {
				throw new Exception("emptyCategory:" + re.getPropertyCategory());
			}
			// Try to pull constant from map
			PropertyDTO keyValue = hmCat.get(re.getProperty());
			if (keyValue == null) {
				log.error("keyValue not found:" + re.getProperty());
				// Try to load Category since key may have been added since last
				// try
				hmCat = cacheCategory(re, true);
				keyValue = hmCat.get(re.getProperty());
				if (keyValue == null) {
					throw new Exception("propertyNotFound:" + re.getPropertyCategory() + "." + re.getProperty());
				}
			}
			log.debug("Value found:" + keyValue.getPropertyValue());
			String value = PropertyEncryptUtil.decryptString(keyValue.getPropertyValue());
			return value;
		} catch (Exception ex) {
			log.error("Unable to get property value due to backend exception", ex);
			throw ex;
		}
	}
	
	
	/**
	 * Looks up the constant category and caches it in the hashmap
	 * 
	 * @param re
	 *            Enum of the property being looked up
	 * @param forceReload
	 *            Set to true if the category should be looked up again/reloaded
	 * @return Returns the Map that represents the all the properties in the
	 *         category of the PropertyEnum passed
	 * @throws Exception
	 *             Thrown if a data access error occurs
	 */
	public static Map<String, PropertyDTO> cacheCategory(IPropertyEnum re, boolean forceReload) throws Exception {
		log.trace("cacheCategory:" + re.getPropertyCategory());
		Map<String, PropertyDTO> propertyMap = categoriesMap.get(re.getPropertyCategory());
		// If the cache does not already exists then fetch it
		if (propertyMap == null || forceReload) {
			log.debug("Cache Miss for constants, request category from SBO");

			try {
				IPropertySvc propertySvc = (IPropertySvc) ServiceFactory.getService("propertysvc");
				propertyMap = propertySvc.getPropertyMapByCategoryId(re.getPropertyCategory());
				categoriesMap.put(re.getPropertyCategory(), propertyMap);
			} catch (Exception ex) {
				log.error("Unable to get properties for category:" + re.getPropertyCategory() + ":", ex);
				throw ex;
			}
		} else {
			log.debug("Cache Hit for constants");
		}
		return propertyMap;
	}

	/**
	 * This will clear all of the cached values, and force a reload next time
	 * one of the constants is referenced.
	 */
	public static void clearCache() {
		log.trace("clearCache");
		categoriesMap.clear();
	}
}
