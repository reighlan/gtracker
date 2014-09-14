package psych.server.data.util.enums;

/**
 * Interface all Property enums must implement. Forces all properties to have an
 * enum and thus be easier to track
 * 
 * @version 2.0
 * 
 * @author Toby Boyd
 */
public interface IPropertyEnum {

	/**
	 * Get the Property Category
	 * 
	 * @return The Property Category
	 */
	public String getPropertyCategory();

	/**
	 * Get the Property
	 * 
	 * @return The Property
	 */
	public String getProperty();

}
