package psych.server.data.enums.props;

import psych.server.data.util.enums.IPropertyEnum;

/**
 * Properties related to the email service
 * 
 * @version 2.0
 * 
 * @author Toby Boyd
 */
public enum GeneralEmailProps implements IPropertyEnum {

	// Values starting with MAIL are part of the mail protocol and put into a
	// properties file by the service. Values starting with TB_MAIL are
	// extensions to the basic mail service

	// Set this to true to force all email to the same TO address
	TB_MAIL_DEBUG("tbmail.debug"),
	// Address to send all email to when in debug mode
	TB_MAIL_DEBUG_FORCE_TO("tbmail.force.to.address"),
	// Default from address normally a no-reply@...
	TB_MAIL_DEFAULT_FROM("tbmail.default.from"),
	// user for the smtp server
	MAIL_USER("mail.user"),
	// password for the smtp server
	MAIL_PASSWORD("mail.password"),
	// smtp host address
	MAIL_HOST("mail.host"),
	// protocol for the smtp server
	MAIL_TRANSPORT_PROTOCOL("mail.transport.protocol"),
	// Account submitting to the smtp server, often the same as the MAIL_USER
	MAIL_SMTP_SUBMITTER("mail.smtp.submitter"),
	// Set to true if SMTP auth is to be used
	MAIL_SMTP_AUTH("mail.smtp.auth"),
	// set to true if tls is being used as part of the auth
	MAIL_SMTP_TLS("mail.smtp.tls"),
	// smtp port to use
	MAIL_STMP_PORT("mail.smtp.port");

	private static final String CATEGORY = "com.crashpost.hub.email";
	private final String property;

	/**
	 * Constructor
	 * 
	 * @param propPath
	 *            Path to the property in the property file
	 */
	private GeneralEmailProps(String property) {
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
