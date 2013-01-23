package org.pjr.rulesengine.propertyloader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The Class PropertyLoader.
 *
 * @author Sudhakar
 */
public class PropertyLoader {
	
	/** The Constant PROPERTY_FILE_NAME. */
	private static final String PROPERTY_FILE_NAME ="sql_queries.properties";

	/** The properties. */
	private static Properties properties;
	

	
	
	static{
		properties = new Properties();
		InputStream inputStream  = null;
		try {
			System.out.println();
			inputStream = 
				PropertyLoader.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME);			
			properties.load(inputStream);
			System.out.println("Loading Application Properties - Completed");
		} catch (FileNotFoundException fileNotFoundException) {			
			
		}catch (IOException ioException) {			
						
		}finally{
			try {
				inputStream.close();
			} catch (Exception e) {
				System.out.println(PROPERTY_FILE_NAME+ "Property Load Exception :"+e.getMessage());
			}
		}

	
		
	

	}
	
	/**
	 * Gets the property.
	 * 
	 * @param propertyKey the property key
	 * 
	 * @return the property
	 */
	public static String getProperty(String propertyKey){
		String propertyValue = properties.getProperty(propertyKey);
	    System.out.println("Property Value "+ propertyValue);
		return propertyValue;
	}   
}
