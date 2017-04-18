package com.xiaohongshu.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.LoggerFactory;

/**
 * @author tianchen
 * 
 */
public class PropertiesReader {
	private static Properties prop = new Properties();

	public static void initialize(String fileName) {
		try {
			LoggerFactory.getLogger(PropertiesReader.class).info("Loading properties file=" + fileName);
			String test = PropertiesReader.class.getClassLoader().getResource("").getPath();

			InputStream in = PropertiesReader.class.getClassLoader()
					.getResourceAsStream(fileName);
		
			Properties temp = new Properties();
			temp.load(in);
			// Merge temp & prop ;
			merge(temp);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	private static void merge(Properties temp) {
		Enumeration<String> propertyNames = (Enumeration<String>) temp
				.propertyNames();
		while (propertyNames.hasMoreElements()) {
			String nextElement = propertyNames.nextElement();
			System.setProperty(nextElement,temp.getProperty(nextElement));//Inject to system properties...
			prop.setProperty(nextElement, temp.getProperty(nextElement));
		}
	}

	public static String loadValue(String key) {
		return prop.getProperty(key);
	}
}
