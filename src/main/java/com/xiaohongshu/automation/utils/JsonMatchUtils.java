package com.xiaohongshu.automation.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.LoggerFactory;

/**
 * @author lingxue created on 6/29/16
 * @version v0.1
 **/

public class JsonMatchUtils {

    public static boolean matchJson(String actual,String expect){
        boolean result = false;
        try{
            JSONObject actualJson = JSON.parseObject(actual);
            JSONObject expectJson = JSON.parseObject(expect);
            result = expectJson.equals(actualJson);
        }
        catch (Exception e){
            LoggerFactory.getLogger(JsonMatchUtils.class).error("Json Match过程中出错: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    public static void main(String[] args){
        String a = "{\"a\":1,\"b\":2}";
        String d =  "{\"a\":1,\"b\":3}";
        System.out.println(matchJson(a,d));
    }


}
