package com.xiaohongshu.automation.service.tesla;

import com.xiaohongshu.automation.service.common.CommonService;
//import com.mogujie.qa.intertest.support.BaseResult;
//import com.mogujie.qa.intertest.support.Result;
//import com.mogujie.service.datamatrix.domain.dto.Tesla;
//import com.mogujie.service.datamatrix.domain.result.PlainResult;
//import com.mogujie.service.datamatrix.impl.InvokeTeslaServiceImpl;
//import com.mogujie.trace.lurker.LurkerAgent;
//import com.mogujie.trace.lurker.TraceContextUtils;
//import com.mogujie.trace.lurker.TraceIdGenerator;
import org.slf4j.LoggerFactory;


/**
 * @author lingxue created on 4/21/16
 * @version v0.1
 **/

public class TeslaTest extends CommonService {

    public TeslaTest() {
        para = new TeslaPara();
    }

    public boolean execute() {
        LoggerFactory.getLogger(TeslaTest.class).info("----------Enter TeslaTest");
        if (((TeslaPara) para).getService() == null) {
            LoggerFactory.getLogger(TeslaTest.class).info("Execute Fail Due to service null");
            return false;
        }
        //判断是否是压测标示
//        if(((TeslaPara) para).isLurker()){
//            String context = TraceIdGenerator.generateTraceContext(1, "lst=1", false);
//            TraceContextUtils.setCurrentTraceContext(new LurkerAgent(context));
//            LoggerFactory.getLogger(TeslaTest.class).info("this is stress request ! lurker is true ");
//        }
//
//        try {
//            Tesla tesla = new Tesla(((TeslaPara) para).getService(), ((TeslaPara) para).getMethod(), Arrays.asList(((TeslaPara) para).getInclass().split(" , ")),
//                    Arrays.asList(((TeslaPara) para).getInvalue().split(" ; ")), ((TeslaPara) para).getIp(), ((TeslaPara) para).getGroup(), ((TeslaPara) para).getVersion(), ((TeslaPara) para).getTimeout());
//            InvokeTeslaServiceImpl invokeTeslaService = new InvokeTeslaServiceImpl();
//            PlainResult<Object> result = invokeTeslaService.invoke(tesla);
//            Result br = new Result();
//            br.setReturnObj(result.getData());
//            if (result.isSuccess()) {
//                br.setResultType(BaseResult.ResultType.PASSED);
//                response = br;
//                LoggerFactory.getLogger(TeslaTest.class).info("Execute Result = Pass");
//                return true;
//            } else {
//                br.setResultType(BaseResult.ResultType.FAILED);
//                br.setFailedMsg(result.getMessage());
//                response = br;
//                LoggerFactory.getLogger(TeslaTest.class).info("Execute Result = Fail, the response is: " + result);
//                return false;
//            }
//        } catch (Exception e) {
//            LoggerFactory.getLogger(TeslaTest.class).error("Execute Fail Exception");
//            e.printStackTrace();
//            return false;
//        }
        return false;
    }

//    public String getResultType() {
//        if (response instanceof BaseResult) {
//            return ((BaseResult) response).getResultType().toString();
//        } else if (response instanceof String) {
//            return (String) response;
//        } else {
//            LoggerFactory.getLogger(TeslaTest.class).info("No Result Type");
//            return "";
//        }
//    }
//
//    public String getResponseData() {
//        if (response instanceof Result) {
//            System.out.println(((Result) response).getReturnObj());
//            return JSONObject.toJSONString(((Result) response).getReturnObj(), SerializerFeature.WriteNonStringKeyAsString);
//        } else {
//            LoggerFactory.getLogger(TeslaTest.class).info("No Response Data");
//            return "";
//        }
//    }

    public void setPara(TeslaPara para) {
        this.para = para;
    }

    public TeslaPara getPara() {
        return (TeslaPara) para;
    }

    public static void main(String[] args) {
        TeslaTest teslaTest = new TeslaTest();
        teslaTest.setDataFile("/Users/zhanghao/Documents/Work/Automation/Hydra/js/Demo/tesla/tesla.json");
        System.out.println(teslaTest.execute());
//        if(teslaTest.execute()){
        //       System.out.println(teslaTest.getResponse());
//            System.out.println(teslaTest.getResponseData());
//            System.out.println(teslaTest.getResultType());
//        }

//        TeslaTest teslaTest = new TeslaTest();
//        TeslaPara para = new TeslaPara();
//        para.setService("com.mogujie.pagani.api.service.PaganiApiService");
//        para.setMethod("search");
//        para.setInclass("com.mogujie.pagani.api.model.PaganiQuery");
//        para.setInvalue("{}");
//        para.setVersion("1.0.0");

//        para.setService("com.mogujie.service.pay.hongbao.api.HongbaoService");
//        para.setMethod("getHongbaoList");
//        para.setInclass("long");
//        para.setInvalue("11");


//        teslaTest.setPara(para);
//        if(teslaTest.execute()){
//            System.out.println(teslaTest.getResponse());
//            System.out.println(teslaTest.getResponseData());
//            System.out.println(teslaTest.getResultType());
//        }
    }

}
