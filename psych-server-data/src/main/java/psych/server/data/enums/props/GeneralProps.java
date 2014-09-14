package psych.server.data.enums.props;

import psych.server.data.util.enums.IPropertyEnum;

/**
 * General properties Enum for accessing properties
 * 
 * @version 2.0
 * 
 * @author Toby Boyd
 */
public enum GeneralProps implements IPropertyEnum {
	// base fully qualified url for the consumer auth site
	BASE_URL("base.client.url"),
	// Full path to where velocity templates are stored
	VELOCITY_TEMPLATE_HOME("base.velocity.templates"),
	// To Email for Device Changes
	DEVICE_CHANGE_EMAIL_TO("base.device.change.to.email"),
	// Lockout max, set to -1 for no lockout
	LOCK_OUT_CNT("auth.lockoutcnt");

	private static final String CATEGORY = "com.crashpost.hub.general";
	private final String property;

	/**
	 * Constructor
	 * 
	 * @param propPath
	 *            Path to the property in the property file
	 */
	private GeneralProps(String property) {
		this.property = property;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getPropertyCategory() {
		return CATEGORY;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getProperty() {
		return property;
	}

}
