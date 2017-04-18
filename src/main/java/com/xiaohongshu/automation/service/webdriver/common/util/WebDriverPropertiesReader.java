package com.xiaohongshu.automation.service.webdriver.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class WebDriverPropertiesReader {
    public int size;
    private Properties prop;

    {
        prop = new Properties();
        size = 0;
    }

    public WebDriverPropertiesReader(String fileName) {
        try {
            InputStream in = WebDriverPropertiesReader.class.getClassLoader().getResourceAsStream(fileName);
            prop.load(in);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int sizeOfProperties() {
        return prop.size();
    }

    public String loadValue(String key) {
        return prop.getProperty(key);
    }

    public String[] loadValues() {
        String[] values = new String[prop.size()];
        Enumeration em = prop.keys();
        int i = 0;
        while (em.hasMoreElements()) {
            values[i] = prop.getProperty((String) em.nextElement());
            i++;
        }
        return values;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
