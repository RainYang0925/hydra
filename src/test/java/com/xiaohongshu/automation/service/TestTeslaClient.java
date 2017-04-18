package com.xiaohongshu.automation.service;

import com.xiaohongshu.automation.service.tesla.TeslaPara;
import com.xiaohongshu.automation.service.tesla.TeslaTest;
//import com.mogujie.metabase.serialization.JsonUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arthur on 17/1/9.
 */
public class TestTeslaClient {

    @Test
    public void testTesla(){
        String service = "com.mogujie.dfpdistribute.service.DfpService" ;
        String method = "search" ;
//        String allInfoFromService = TeslaParaHelper.getAllInfoFromService(service);
//        String sam = TeslaParaHelper.getInValueFromServiceAndMethod(service, method);
//        System.out.println(sam);

        TeslaTest teslaTest = new TeslaTest();
        TeslaPara para = new TeslaPara();
        para.setService(service);
        para.setMethod("search");
        para.setInclass("java.util.Map");
        para.setInvalue("{\"tdid\":\"aaa\"}");
        teslaTest.setPara(para);
        boolean result = teslaTest.execute();
        System.out.println(result);

    }


    @Test
    public void testTeslaNative(){
//        String service = "com.mogujie.dfpdistribute.service.DfpService" ;
//        ReferConfig<? extends Object> refConf = new ReferConfig(service);
//        refConf.setVersion("1.0.0");
//        List<MethodSpecials> methods = new ArrayList<>();
//        MethodSpecials method = new MethodSpecials("search");
//        method.setParameterTypes("java.util.Map");
//        methods.add(method);
//        refConf.setMethodSpecialsList(methods);
//        try {
//            Object serviceConsumer = (Object)TeslaServiceConsumerFactory.getTeslaServiceConsumer(refConf);
//            System.out.println(serviceConsumer);
//
//            System.out.println(JSON.toJSONString(null));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void testJSONUtil(){
        List<String> arr = new ArrayList<>();
        arr.add("10.13.127.15");
        arr.add("10.16.22.22");
//        JsonUtils.object2Json(arr);
    }
}
