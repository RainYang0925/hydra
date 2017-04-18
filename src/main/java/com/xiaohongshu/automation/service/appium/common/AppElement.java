package com.xiaohongshu.automation.service.appium.common;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by yuanfei on 16/5/26.
 */
public class AppElement {

    /*
    ** 设置查找元素方式的枚举类
     */

    public enum IdentifyType {

        ID, XPATH, TAGNAME, LINKTEXT, NAME, CLASSNAME, PARTIALLINKTEXT, CSSSELECTOR,ACCESSIBILITYID
    }

    /*
   ** 找到页面元素
   ** @param elementPath:查找元素的路径
    */
    public static WebElement getElement(String elementPath,AppiumDriver webDriver){
        WebElement element =null;
        try{

        String [] parseStrings = elementPath.split("://");
        String identifyType = parseStrings[0];
        String identifyValue = parseStrings[1];
        switch(IdentifyType.valueOf(identifyType.toUpperCase())){
            case XPATH:
                try{
                    element = webDriver.findElementByXPath(identifyValue);
                }catch (Exception e){
                    LoggerFactory.getLogger(AppElement.class).info("WebElement is not found");
                }
                break;
            case CLASSNAME:
                try{
                    element = webDriver.findElementByClassName(identifyValue);
                }catch (Exception e){
                    LoggerFactory.getLogger(AppElement.class).info("WebElement is not found");
                    e.printStackTrace();
                }
                break;
            case ACCESSIBILITYID:
                try {
                    element = webDriver.findElementByAccessibilityId(identifyValue);
                    }catch (Exception e){
                    LoggerFactory.getLogger(AppElement.class).info("WebElement is not found");
                e.printStackTrace();
            }
            break;
            case ID:
                try {
                    element = webDriver.findElementById(identifyValue);
                }
                catch (Exception e){
                    LoggerFactory.getLogger(AppElement.class).info("WebElement is not found");
                e.printStackTrace();
            }
            break;
            default:
                break;
        }


        }catch( Exception e){
            e.printStackTrace();
        }

        return element;

}

    /*
   ** 找到页面元素列表
   ** @param elementPath:查找元素的路径
    */
    public static List<WebElement> getElements(String elementPath,AppiumDriver webDriver){
        List <WebElement> elements =null;
        try{

            String [] parseStrings = elementPath.split("://");
            String identifyType = parseStrings[0];
            String identifyValue = parseStrings[1];
            switch(IdentifyType.valueOf(identifyType.toUpperCase())){
                case XPATH:
                    elements = webDriver.findElementsByXPath(identifyValue);
                    break;
                case CLASSNAME:
                    elements = webDriver.findElementsByClassName(identifyValue);
                    break;
                case ACCESSIBILITYID:
                    elements = webDriver.findElementsByAccessibilityId(identifyValue);
                    break;
                case ID:
                    elements=webDriver.findElementsById(identifyValue);
                default:
                    break;
            }


        }catch( Exception e){
            e.printStackTrace();
        }

        return elements;

    }}
