describe("test get store tabs", function(){
    it("should return true", function(){
        var restfulUtils = new RestfulUtils();
        var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+ '/12000000009.json'));
        var login = require('js/common/login/login.js');
        var sessionId = login.login(phoneInfo.phone, phoneInfo.deviceId);
        var reqURL = "http://sit.test.xiaohongshu.com/api/store/v1/tabs?platform=Android"+"&deviceId="+phoneInfo.deviceId+"&versionName=4.17.010&channel=YingYongBao&sid="+sessionId+"&lang=zh-CN";
        restfulUtils.setServerURL(reqURL+ "_debug_=WHOSYOURDADDY");
        restfulUtils.setHttpMethod("get");
        restfulUtils.execute();

        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        expect(jsonObj.success).toBe(true);
        caseResult.setResultMsg(jsonObj.success);
    });
});