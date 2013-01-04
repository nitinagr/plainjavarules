package org.pjr.rulesengine.util;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AccessProperties {

	private static final Log log = LogFactory.getLog(AccessProperties.class);
	
	private Properties properties;

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	
	public String getFromProps(String key){
		String returnValue = null;
		
		try {
			returnValue = properties.getProperty(key);
			log.debug("returnValue :"+returnValue);
		} catch (Exception e) {		
		}
		
		return returnValue;
	}
	
	
}
