
describe("get login session",function(){

    it("get session via login api ",function(){
        var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12000000009.json'));
        var login = require('js/common/login/login');
        var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);
        caseResult.setResultMsg(sessionId);
    });

});