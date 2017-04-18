package com.xiaohongshu.automation.utils;

import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * @author lingxue created on 6/22/16
 * @version v0.1
 **/

public class ResourceUtils {

    public static String getResoucePath(String resouce){
        URL url = ResourceUtils.class.getClassLoader().getResource(resouce);
        if(url == null){
            LoggerFactory.getLogger(ResourceUtils.class).error("resource not exist: " + resouce);
            return "";
        }
        return url.getPath();
    }

    public static String encodeForJSPath(String path){
        return path.replace("\\","/");
    }
}
