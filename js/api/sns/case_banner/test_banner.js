
describe("test get banner...", function(){
    it("should return OK...",function(){
        var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12000000009.json'));
        var login = require('js/common/login/login');
        var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

        var reqURL = "http://sit.test.xiaohongshu.com/api/sns/v4/explore/banner?deviceId="+phoneInfo.deviceId+"&lang=zh&platform=iOS&sid="+sessionId+"_debug_=WHOSYOURDADDY"
        var restfulUtils = new RestfulUtils();
        restfulUtils.setServerURL(reqURL);
        restfulUtils.execute();
        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        expect(jsonObj.result).toBe(0);
        expect(jsonObj.data.events.length).toBeGreaterThan(1);
        caseResult.setResultMsg(jsonObj.result);
    });
});