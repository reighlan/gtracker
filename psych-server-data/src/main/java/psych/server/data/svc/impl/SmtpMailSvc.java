package psych.server.data.svc.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import psych.server.data.enums.props.GeneralEmailProps;
import psych.server.data.util.PropertyUtil;

/**
 * Uses the javax.mail API to send emails to contacts. USAGE: must call open
 * connection and close connection. They are not called for you. Call open
 * connection send all the mail then close the connection. If there will be long
 * gaps then close and reopen the connection it will timeout eventually
 * 
 */
public class SmtpMailSvc {

	private Logger log = Logger.getLogger(SmtpMailSvc.class);
	private Session mailSession;
	private Transport transport;

	// General email properties used by javax.mail
	private Properties props;

	// Debug mode
	private boolean debugTO;
	private String forceEmailTO;

	/**
	 * Instantiates the object. Loads the properties for sending the email.
	 * Utilizes the mail.propertis file in the class path
	 * 
	 * @throws Exception
	 *             an exception occurred reading the properties file
	 */
	public SmtpMailSvc() throws Exception {
		log.trace("SmtpMailSvc");
		this.props = buildGeneralProps();
		loadDebugProperties();
	}

	/**
	 * Closes the connection to the SMTP server.
	 * 
	 */
	public void closeConnection() {
		log.trace("closeConnection");
		try {
			if (transport != null) {
				transport.close();
			}
		} catch (Exception ex) {
			log.warn("Unable to close smtp connection", ex);
		}
	}

	/**
	 * Opens the connection to the SMTP Server.
	 * 
	 * @throws MessagingException
	 *             an exception occurs opening the connection
	 */
	public void openConnection() throws MessagingException {
		log.trace("openConnection");

		Authenticator authenticator = new Authenticator(PropertyUtil.getPropertyValue(GeneralEmailProps.MAIL_USER),
				PropertyUtil.getPropertyValue(GeneralEmailProps.MAIL_PASSWORD));
		mailSession = Session.getInstance(props, authenticator);
		mailSession.setDebug(log.isDebugEnabled());
		transport = mailSession.getTransport();
		transport.connect();
	}

	/**
	 * Overload for postMail that only sends to a single recipient
	 * 
	 * @param recipient
	 *            Email address to send the email to
	 * @param subject
	 *            subject of the email
	 * @param message
	 *            message / body of the email
	 * @param from
	 *            from address for the email
	 * @throws MessagingException
	 *             thrown if the email could not be sent
	 */
	public void sendHTMLEmail(String recipient, String subject, String message, String from) throws MessagingException {
		log.trace("postMail");

		//Build TO list
		if (recipient.contains(",")) {
			log.debug("Receipient contained a comma splitting and ending to all recipients:" + recipient);
			String[] toArray = recipient.split(",");

			sendHTMLEmail(toArray, subject, message, from);
		}else{
			sendHTMLEmail(new String[] { recipient }, subject, message, from);
		}
		
	}

	/**
	 * Send an html based email
	 * 
	 * @param recipients
	 *            array of email addresses
	 * @param subject
	 *            subject of the email
	 * @param body
	 *            message / body of the email
	 * @param from
	 *            from address for the email
	 * @throws MessagingException
	 *             thrown if the email could not be sent
	 */
	public void sendHTMLEmail(String recipients[], String subject, String body, String from) throws MessagingException {
		log.trace("sendHTMLEmail");

		if (debugTO) {
			String oldTo = flattenRecipients(recipients);
			subject = subject + " TO:" + oldTo;
			recipients = new String[] { forceEmailTO };

		}

		MimeMessage message = new MimeMessage(mailSession);
		message.setSubject(subject);
		message.setFrom(new InternetAddress(from));
		message.setContent(body, "text/html");
		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		message.setRecipients(Message.RecipientType.TO, addressTo);
		if (log.isDebugEnabled()) {
			printMessage(recipients, subject, body, from);
		}
		transport.sendMessage(message, addressTo);
	}

	/**
	 * Check the properties and put the service into debug mode with a forced TO
	 * address if debug mode is set in the properties
	 */
	private void loadDebugProperties() {
		log.trace("loadDebugProperties");

		String debugStr = PropertyUtil.getPropertyValueNull(GeneralEmailProps.TB_MAIL_DEBUG);
		if (debugStr != null && debugStr.equals("true")) {
			debugTO = true;
			forceEmailTO = PropertyUtil.getPropertyValue(GeneralEmailProps.TB_MAIL_DEBUG_FORCE_TO);
			log.info("SMTP server is in debug mode sending all emails to:" + forceEmailTO);
		}

	}

	/**
	 * Print out the message for debugging purposes or auditing
	 * 
	 * @param recipients
	 *            array of email addresses
	 * @param subject
	 *            subject of the email
	 * @param body
	 *            message / body of the email
	 * @param from
	 *            from address for the email
	 */
	private void printMessage(String recipients[], String subject, String body, String from) {
		log.trace("printMessage");

		StringBuilder sb = new StringBuilder();

		for (String to : recipients) {
			sb.append("to:" + to + "\r\n");
		}

		sb.append("Subject:" + subject + "\r\n");
		sb.append("Message:" + body + "\r\n");
		sb.append("From:" + from + "\r\n");
		log.debug("Message to be send:\r\n");

	}

	/**
	 * Turns an array of recipients into a delimited string
	 * 
	 * @param recipients
	 *            Array list of strings representing email addresses
	 * @return String representation of the to email addresses
	 */
	private String flattenRecipients(String recipients[]) {
		log.trace("flattenRecipients");
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String to : recipients) {
			if (!first) {
				sb.append("," + to);
			} else {
				first = false;
				sb.append(to);
			}
		}
		return sb.toString();
	}

	private Properties buildGeneralProps() {
		log.trace("buildGeneralProps");
		Properties props = new Properties();
		props.put("mail.transport.protocol", PropertyUtil.getPropertyValue(GeneralEmailProps.MAIL_TRANSPORT_PROTOCOL));
		props.put("mail.host", PropertyUtil.getPropertyValue(GeneralEmailProps.MAIL_HOST));
		props.put("mail.user", PropertyUtil.getPropertyValue(GeneralEmailProps.MAIL_USER));
		props.put("mail.password", PropertyUtil.getPropertyValue(GeneralEmailProps.MAIL_PASSWORD));
		props.put("mail.smtp.submitter", PropertyUtil.getPropertyValue(GeneralEmailProps.MAIL_SMTP_SUBMITTER));
		props.put("mail.smtp.auth", PropertyUtil.getPropertyValue(GeneralEmailProps.MAIL_SMTP_AUTH));
		props.put("mail.smtp.tls", PropertyUtil.getPropertyValue(GeneralEmailProps.MAIL_SMTP_TLS));
		props.put("mail.smtp.port", PropertyUtil.getPropertyValue(GeneralEmailProps.MAIL_STMP_PORT));
		return props;
	}

}

/**
 * Authenticator class, so that an SMTP server that requires authentication can
 * be used
 * 
 */
class Authenticator extends javax.mail.Authenticator {
	private PasswordAuthentication authentication;

	public Authenticator(String username, String password) {
		authentication = new PasswordAuthentication(username, password);
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return authentication;
	}
}
