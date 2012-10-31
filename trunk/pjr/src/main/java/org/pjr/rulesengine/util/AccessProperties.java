package org.pjr.rulesengine.util;

import java.util.Properties;

public class AccessProperties {

	
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
		} catch (Exception e) {		
		}
		
		return returnValue;
	}
	
	
}
