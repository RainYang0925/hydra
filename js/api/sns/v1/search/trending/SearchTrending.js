
describe("test initial search function", function(){
    it("source home should return OK...",function(){
        var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
        var login = require('js/common/login_d/login_d');
        var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

        var reqURL = "https://d1.xiaohongshu.com/api/sns/v1/search/trending?deviceId="+phoneInfo.deviceId+"&lang=zh&platform=iOS&sid="+sessionId+"&source=home&_debug_=WHOSYOURDADDY"
        var restfulUtils = new RestfulUtils();
        restfulUtils.setServerURL(reqURL);
        restfulUtils.execute();
        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        JSLog.info(jsonObj.data);

        if (jsonObj.data.length<=0){
            caseResult.failWithMsg("返回数据为空failed");
        }



        for (var i=0;i<jsonObj.data.length;i++){

            var item = jsonObj.data[i];

            if (item==null)
                {
                    caseResult.failWithMsg("数据空，blank");
                }
            else
                {
                    JSLog.info("成功返回successfully");
                }
        }


//        var item =jsonObj.data[i];
//                          var itemStr = JSON.stringify(item);
//                          JSLog.info(itemStr);
//                          caseResult.setResultMsg("成功返回" + itemStr);

//            var item = jsonObj.data[i];
//            if (item.type && item.title){
//                caseResult.setResultMsg("成功返回"+item.type + " | "+ item.title);
//            }
//        }
        //expect(jsonObj.data.).toBe("0");
        //caseResult.setResultMsg(jsonObj.result);
        caseResult.setResultMsg(restfulUtils.responseStatusCode);

    });

    it("source explore should return OK...",function(){
        var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
        var login = require('js/common/login_d/login_d');
        var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

        var reqURL = "https://d1.xiaohongshu.com/api/sns/v1/search/trending?deviceId="+phoneInfo.deviceId+"&lang=zh&platform=iOS&sid="+sessionId+"&source=explore&_debug_=WHOSYOURDADDY"
        var restfulUtils = new RestfulUtils();
        restfulUtils.setServerURL(reqURL);
        restfulUtils.execute();
        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        JSLog.info(jsonObj.data);

        if (jsonObj.data.length<=0){
            caseResult.failWithMsg("返回数据为空failed");
        }
        for (var i=0;i<jsonObj.data.length;i++){

                    var item = jsonObj.data[i];

                    if (item==null)
                        {
                            caseResult.failWithMsg("数据空,blank");
                        }
                    else
                        {
                            JSLog.info("成功返回successfully");
                        }

        caseResult.setResultMsg(restfulUtils.responseStatusCode);}
    });

    it("source store should return OK...",function(){
        var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
        var login = require('js/common/login_d/login_d');
        var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

        var reqURL = "https://d1.xiaohongshu.com/api/sns/v1/search/trending?deviceId="+phoneInfo.deviceId+"&lang=zh&platform=iOS&sid="+sessionId+"&source=store&_debug_=WHOSYOURDADDY"
        var restfulUtils = new RestfulUtils();
        restfulUtils.setServerURL(reqURL);
        restfulUtils.execute();
        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        JSLog.info(jsonObj.data);

        if (jsonObj.data.length<=0){
            caseResult.failWithMsg("返回数据为空failed");
        }
        for (var i=0;i<jsonObj.data.length;i++){

                    var item = jsonObj.data[i];

                    if (item==null)
                        {
                            caseResult.failWithMsg("数据空,blank");
                        }
                    else
                        {
                            JSLog.info("成功返回successfully");
                        }

        caseResult.setResultMsg(restfulUtils.responseStatusCode);}

    });

    it("source recommend_user should return OK...",function(){
        var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
        var login = require('js/common/login_d/login_d');
        var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

        var reqURL = "https://d1.xiaohongshu.com/api/sns/v1/search/trending?deviceId="+phoneInfo.deviceId+"&lang=zh&platform=iOS&sid="+sessionId+"&source=recommend_user&_debug_=WHOSYOURDADDY"
        var restfulUtils = new RestfulUtils();
        restfulUtils.setServerURL(reqURL);
        restfulUtils.execute();
        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        JSLog.info(jsonObj.data);

        if (jsonObj.data.length<=0){
            caseResult.failWithMsg("返回数据为空failed");
        }
        for (var i=0;i<jsonObj.data.length;i++){

                    var item = jsonObj.data[i];

                    if (item==null)
                        { caseResult.failWithMsg("数据空,blank");
                        }
                    else
                        {
                        JSLog.info("成功返回successfully");

                        }

        caseResult.setResultMsg(restfulUtils.responseStatusCode);}

    });
});