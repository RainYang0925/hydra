package com.xiaohongshu.automation.service.webdriver.pages;

import com.xiaohongshu.automation.service.webdriver.DefaultPageObject;
import com.xiaohongshu.automation.service.webdriver.common.DriverFactory;
import com.xiaohongshu.automation.service.webdriver.common.JQDriver;
import com.xiaohongshu.automation.service.webdriver.common.PageObject;
import com.xiaohongshu.automation.service.webdriver.common.util.WebDriverHelper;
import com.xiaohongshu.automation.utils.JSLog;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * Created by zren on 17/3/23.
 */
public class WXPageObject extends PageObject {

    private String searchBarLocator = "#search_bar > input";
    WebElement searchBar ;
    private String searchResultLocator = "div.contact_item.on div.info h4.nickname.ng-binding";
    WebElement searchResult ;

    private String editAreaLocator = "#editArea";
    WebElement editArea ;

    private String sendBtnLocator = "#chatArea > div.box_ft.ng-scope > div.action > a.btn.btn_send" ;
    WebElement sendBtn ;

    private JSLog logger;

    public WXPageObject(){
        super(DriverFactory.getDriver());
        setHttps(true);
        setHost("wx.qq.com");
        load();
    }

    @Override
    public String getResourceFilePath() {
        return "page.properties";
    }

    @Override
    public String getContextURL() {
        return null==contextURL?"":contextURL;
    }



    public boolean sendMsgTo(String msg,String user){
        checkLogin();
        boolean ret = false ;
        searchBar.sendKeys(user);
        WebDriverHelper.sleepSecond(1);
        searchResult = getElement(By.cssSelector(searchResultLocator));
        searchResult.click();
        editArea = getElement(By.cssSelector(editAreaLocator));
        editArea.click();
        editArea.sendKeys(msg);
        sendBtn = getElement(By.cssSelector(sendBtnLocator));
        sendBtn.click();
        //JQDriver.getInstance(this.getDriver()).jqueryJS("var msg = $([\n" +
//                "        '.message:not(.me) .bubble_cont > div',\n" +
//                "        '.message:not(.me) .bubble_cont > a.app',\n" +
//                "        '.message:not(.me) .emoticon',\n" +
//                "        '.message_system'\n" +
//                "      ].join(', ')).last(); alert(msg);");
        //WebDriverHelper.sleepSecond(60);
        return ret ;
    }

    private void checkLogin() {
        boolean notLogin = true ;
        while (notLogin){
            try {
                searchBar = this.getElement(By.cssSelector(searchBarLocator));
                if (searchBar.isDisplayed()) {
                    notLogin = false ;
                    break;
                }
            }catch (NoSuchElementException e){
                logger.info("Not login yet, wait for another loop...");
                load();
            }

            WebDriverHelper.sleepSecond(1);
        }


    }
}
