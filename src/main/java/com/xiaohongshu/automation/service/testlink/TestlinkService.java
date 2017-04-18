package com.xiaohongshu.automation.service.testlink;

import com.alibaba.fastjson.JSON;
import com.xiaohongshu.automation.config.GenericConfig;
import com.xiaohongshu.automation.service.restful.RestfulUtils;

import java.util.Map;

/**
 * Created by zren on 17/3/23.
 */
public class TestlinkService {
    private static final String AUTH_KEY_NAME="PHP_AUTH_USER" ;
    private String authKey = GenericConfig.INSTANCE.getConfig("testlink.rest.authkey");
    private String host = GenericConfig.INSTANCE.getConfig("testlink.rest.host");

    public String who(){
        String url = buildRestURL("who");

        RestfulUtils restAction = buildRestAction(url,"get", null);
        return "";
    }

    private RestfulUtils buildRestAction(String url, String method, Map params) {
        RestfulUtils ret = new RestfulUtils();
        ret.setServerURL(url);
        ret.setHttpMethod(method);
        ret.setRequestData("");
        return null ;
    }

    private String buildRestURL(String api) {
        return "http://"+host+"/lib/api/rest/v2/"+api ;
    }

}
