package com.xiaohongshu.automation.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaohongshu.automation.service.restful.RestfulUtils;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author lingxue created on 7/4/16
 * @version v0.1
 **/

public class UserUtils {

    public static String serverURL_online = "http://online.hawkeye.qa.mogujie.org/userFactory/getSign?userId=";
    public static String serverURL_offline = "http://10.13.10.33:9001/userFactory/getSign?userId=";

    public static String getSignFromUid(String uid,boolean online){
        String sign ="";
        if(uid !=null&& uid !="") {
            try {
                RestfulUtils restfulUtils = new RestfulUtils();
                if(!online){
                    uid = String.valueOf(Utils.urlToId(uid));
                }
                restfulUtils.setServerURL((online?serverURL_online:serverURL_offline) + uid);
                restfulUtils.execute();
                String responseString = restfulUtils.getResponseBody();

                JSONObject response = JSON.parseObject(responseString);
                if(restfulUtils.responseStatusCode==200){
                    int code = response.getIntValue("code");
                    if(code == 1001){
                        sign = response.getString("jsonData");
                        if (!online){
                            sign = URLEncoder.encode(sign);
                        }
                    }
                    else{
                        LoggerFactory.getLogger(Utils.class).error("response code is wrong");
                    }
                }
                else{
                    LoggerFactory.getLogger(Utils.class).error("can not get connect");
                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            LoggerFactory.getLogger(Utils.class).error("uid can not be empty!");
        }

        return sign;
    }

    public static String getDecodeSignFromUid(String uid,boolean online) {
            return URLDecoder.decode(getSignFromUid(uid, online));
    }

    public static String getSignFromUid(String uid){
        return getSignFromUid(uid, true);
    }

    public static String getDecodeSignFromUid(String uid) {
        return getDecodeSignFromUid(uid, true);
    }

    public static void main(String[] args){
        System.out.println("dd:" + getSignFromUid("18l372q",false));
        System.out.println("dd:" +getDecodeSignFromUid("18l372q", false));
        System.out.println("dd:" +getSignFromUid("18l372q"));
        System.out.println("dd:" +getDecodeSignFromUid("18l372q"));


    }
}
