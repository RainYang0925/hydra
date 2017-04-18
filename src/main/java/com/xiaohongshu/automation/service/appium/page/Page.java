package com.xiaohongshu.automation.service.appium.page;

import com.alibaba.fastjson.JSON;
import com.xiaohongshu.automation.utils.FileUtils;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lingxue created on 5/23/16
 * @version v0.1
 **/

public class Page {

    private String name;
    private List<Element> elements = new ArrayList<Element>();
    private HashMap<String,Element> elementsMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public void fillMap(){
        if(elementsMap == null){
            elementsMap = new HashMap<String,Element>();
            for(Element e : elements){
                elementsMap.put(e.getId(),e);
            }
        }
    }

    public String getElementPathByID(String id, String os){
        fillMap();
        if(elementsMap.containsKey(id)){
            try{
             switch (os){
                 case "ios":
                    return elementsMap.get(id).getIos();
                 case "android":
                    return elementsMap.get(id).getAndroid();
             }
           }catch(Exception e){
                e.printStackTrace();
            }

        }
        else
        LoggerFactory.getLogger(Page.class).info("Can not find element:" + id + " for " + os + " on page " + name);
        return "";

    }

    public static void main(String[] args){
        try{
            URL url = Page.class.getResource("/pages/HomePage.json");
            String json = FileUtils.readStringFromFile(new File(url.getPath()));
            Page page = JSON.parseObject(json, Page.class);
            System.out.println(page.getElementPathByID("o","ios"));
            System.out.println(page.getElementPathByID("chartButton","android"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
