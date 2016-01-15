package com.nationwide.listener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.nationwide.service.GeoServiceConnectionMngr;

public class PropertiesListener implements ServletContextListener {
	
	private static Logger logger = Logger.getLogger(PropertiesListener.class.getName());
	public static Map<String,String> applicationProperties = new HashMap<String,String>();
	InputStream inputStream;
	
	public void contextInitialized(ServletContextEvent servletContextListener){
		// execute on Start up
		logger.info("Application started!!!");
		applicationProperties = loadProperties();
		getGeoServiceCredentials();
	}
	
	public void getGeoServiceCredentials(){
		GeoServiceConnectionMngr.initGeoService();
	}
	
	public Map<String,String> loadProperties(){
		// access properties data and create a map object
		Map<String, String> propertiesMap = new HashMap<String,String>();
		
		try{
			Properties props = new Properties();
			String propFilename = "config.properties";
			inputStream = getClass().getClassLoader().getResourceAsStream(propFilename);
			if (inputStream != null){
				props.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFilename + "' not found in the classpath");
			}
			
			Enumeration<?> e = props.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = props.getProperty(key);
				logger.info("Key : " + key + ", Value : " + value);
				propertiesMap.put(key, value);
			}
		}
		catch(IOException ex){
			System.out.println("Properties file not found" + ex.getMessage());
		}
		return propertiesMap;
	}
	
	public void contextDestroyed(ServletContextEvent servletContextListener){
		// execute on Shut down
	}
	
	
	/*
	 * Add this class to the web.xml
	 * <listener>
	 * 	<listener-class>
	 * 		com.nationwide.listener.PropertiesListener
	 * 	</listener-class>
	 * </listener>
	 */
}
