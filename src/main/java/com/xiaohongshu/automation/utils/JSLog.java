package com.xiaohongshu.automation.utils;

import org.slf4j.LoggerFactory;

/**
 * @author lingxue created on 6/22/16
 * @version v0.1
 **/

public class JSLog {

    public static void info(String msg){
        LoggerFactory.getLogger(JSLog.class).info(msg);
    }

    public static void error(String msg){
        LoggerFactory.getLogger(JSLog.class).error(msg);
    }

}
