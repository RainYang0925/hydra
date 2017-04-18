
describe("test get me info...", function(){
    it("should return OK...",function(){
        var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12000000009.json'));
        var login = require('js/common/login/login');
        var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

        var reqURL = "http://sit.test.xiaohongshu.com/api/sns/v2/user/me?deviceId="+phoneInfo.deviceId+"&lang=zh&platform=iOS&sid="+sessionId
        var restfulUtils = new RestfulUtils();
        restfulUtils.setServerURL(reqURL);
        restfulUtils.execute();
        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        expect(jsonObj.data.userid).toBe("5821dc80b25a3b01b2e63f14");
        caseResult.setResultMsg(jsonObj.data.userid);
    });
});