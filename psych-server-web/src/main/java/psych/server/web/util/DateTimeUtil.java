package psych.server.web.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Methods to help with dates and times
 * 
 * @author tobyboyd
 * 
 */
public class DateTimeUtil {
	
	private static DateTimeFormatter fmt = DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'");
	
	private static final Logger log = LoggerFactory.getLogger(DateTimeUtil.class);

	public static String getFormattedDate(DateTime dateTime){
		if(log.isTraceEnabled()){
			log.trace("getFormattedDate");
		}
		return dateTime.toString(fmt);
	}
	
}
