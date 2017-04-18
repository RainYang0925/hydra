package com.xiaohongshu.automation.config;

import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author lingxue created on 6/23/16
 * @version v0.1
 **/

public class PropertiesReader {

	public int size;
	boolean looadSystemProperties = false;
	private Properties prop ;
	{
		prop = new Properties();
		size = 0;
	}

	public PropertiesReader(String fileName,boolean loadSystemProperties)
	{
		this.looadSystemProperties = loadSystemProperties;
		if (looadSystemProperties) {
			prop.putAll(System.getProperties());
		}

		importConfigFile(fileName);
	}

	public PropertiesReader(String fileName){
		this(fileName,false);
	}


	public void importConfigFile(String fileName){
		try {
			InputStream in = PropertiesReader.class.getClassLoader().getResourceAsStream(fileName);
			if(in == null){
				in = new FileInputStream(fileName);
			}
			Properties newProp = new Properties();
			newProp.load(in);
			//prop.merge()
			prop.putAll(newProp);
		} catch (Exception e) {
			LoggerFactory.getLogger(PropertiesReader.class).
					error("Exception when load property file: " + fileName);
			e.printStackTrace();
		}
	}

	public Properties getProp(){
		return prop;
	}

    public int sizeOfProperties()
    {
    	return prop.size();
    }
	
    public String loadValue(String key){
    	return prop.getProperty(key);
    }

	public  String[] loadValues()
	{
		String[] values = new String[sizeOfProperties()];
		Enumeration em = prop.keys();
		int i = 0;
		while(em.hasMoreElements())
		{
		   values[i]= loadValue((String)em.nextElement());
		   i++;
		}
		return values;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		JSPropertiesReader readerPackage = new JSPropertiesReader("js.package.properties");
//
//		   String[] propertiesValue = readerPackage.loadValues();
//		   for(int i = 0; i < readerPackage.sizeOfProperties(); i++)
//		   {
//			   Logger.info(propertiesValue[i]);
//		   }
		   
	}

}
