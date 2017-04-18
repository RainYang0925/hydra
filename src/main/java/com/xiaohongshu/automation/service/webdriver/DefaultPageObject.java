/**
 * 
 */
package com.xiaohongshu.automation.service.webdriver;

import com.xiaohongshu.automation.service.webdriver.common.PageObject;
import com.xiaohongshu.automation.service.webdriver.common.util.WebDriverHelper;

/**
 * @author tianchen
 *
 */
public class DefaultPageObject extends PageObject {


	public DefaultPageObject(String host , String port){
		this(host,port,true);

	}

	public DefaultPageObject(String host, String port,boolean load){
		super(host,port);
		if (load){
			load();
		}
	}
	/**
	 * 返回这个页面的配置文件, 包含了元素的信息
	 * @return
     */
	@Override
	public String getResourceFilePath() {
		//return a default properties file... may a empty file...
		return "page.properties";
	}

	@Override
	public String getContextURL() {
		// TODO Auto-generated method stub
//		String url = "" ;
//		if (null!=driver){
//			try {
//				url = driver.getCurrentUrl();
//			}catch(Exception e){}
//		}
		//return "/search/searchByKeyWord";
		return null==contextURL?"":contextURL;
	}

	public static void main(String[] args){
		DefaultPageObject p = new DefaultPageObject("www.baidu.com", null);

		try {
			p.load();
			p.clearAndType(p.getElement("search"), "tianchen");
			p.click(p.getElement("serachButton"));
			WebDriverHelper.sleepSecond(5);

			p.closeBrowser();
		}catch (Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}finally {
			p.closeBrowser();
		}
	}

}
