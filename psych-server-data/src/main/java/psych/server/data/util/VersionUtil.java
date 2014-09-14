package psych.server.data.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Gets the version of an application assuming the info is in a propert file
 * with the common structure defined
 * 
 * @author tobyboyd
 * 
 */
public class VersionUtil {

	private static final Log log = LogFactory.getLog(VersionUtil.class);
	
	public static String getVersionString(String propertyFile) throws Exception{
		log.trace("getVersionString");
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();           
		InputStream stream = loader.getResourceAsStream(propertyFile);
		prop.load(stream);
		String commitID = prop.getProperty("commit_hash");
		String commitBranch = prop.getProperty("commit_branch");
		
		return "Git|" + commitBranch + ":" + commitID;
		
	}
	
	
}
