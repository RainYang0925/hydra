package com.xiaohongshu.automation.service.webdriver.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Check whether target Page contains jquery or not. If no jquery available.
 * Inject a jquery into the page to help Driver manipulate the Page Element.
 * 
 * @author tianchen
 * 
 */
public class JQDriver {
	WebDriver driver = null;
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	JavascriptExecutor jsExe = null;
	String jquery_file_name = "jquery.min.js";
	//Fixed by Scott 2012/07/31 : Move this line into the method injectJquery to avoid exceptions if no jquery.min.js file exist
	//String jquery_content = readJquery();
	String jquery_content = "";

	public String getJqueryJS() {
		return jquery_content;
	}

	public boolean hasJquery() {
		boolean ret = true;
		String checkJS = "return typeof(jQuery);";
		Object undefinedJQuery = jsExe.executeScript(checkJS);
		if ("undefined".equalsIgnoreCase(undefinedJQuery.toString())) {
			ret = false;
		}
		return ret;
	}

	/**
	 * try to inject the JQuery js into the driver page.
	 */
	public void injectJquery() {
		if (!hasJquery()) {
			//Added by scott 2012/07/31
			jquery_content = readJquery();
			jsExe.executeScript(jquery_content);
		}

	}

	/**
	 * I have modified the jquery.min.js ,and put all the content into 1 line js
	 * file.
	 * 
	 * @return
	 */
	private String readJquery() {
		StringBuilder sb = new StringBuilder();
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(jquery_file_name);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try {
			// since the file is only 1 line js code, so this should works as
			// expected.
			sb.append(br.readLine());
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	private static final JQDriver inst = new JQDriver();

	public static final JQDriver getInstance(WebDriver dr) {
		inst.driver = dr;
		inst.jsExe = (JavascriptExecutor) dr;
		
		return inst;
	}

	private JQDriver() {

	}
	
	public WebElement[] jqSearch(String jqSelector){
		String jqJS = "return $('"+jqSelector+"');" ;
		ArrayList<WebElement> elements = (ArrayList<WebElement>) jsExe.executeScript(jqJS);
		return elements.toArray(new WebElement[]{});
		
	}
	
	public void jqueryJS(String js){
		jsExe.executeScript(js);
	}

}
