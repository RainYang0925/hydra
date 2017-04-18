
describe("获取Banner搜索列表 /api/store/ps/banners", function(){

    it("keyword=香水 return OK ", function(){

        var restfulUtils = new RestfulUtils();
        var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+ '/12000000009.json'));
        var login = require('js/common/login_t/login_t.js');
        var sessionId = login.login(phoneInfo.phone, phoneInfo.deviceId);

        var reqURL = "http://t1.b.xiaohongshu.com/api/store/ps/banners?keyword=香水&page=1&per_page=20&session="+ sessionId+ "&deviceId="+ phoneInfo.deviceId;
        restfulUtils.setServerURL(reqURL+ "&_debug_=WHOSYOURDADDY");
        restfulUtils.setHttpMethod("get");
        restfulUtils.execute();

        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        expect(jsonObj.result).toBe(0);

        var bannersAmount = jsonObj.data.banners.length;
        if(bannersAmount == 0){
           caseResult.failWithMsg("搜索结果banner列表为空");
          }
          else{
                for(var i= 0; i< bannersAmount; i++){
                    var banner= jsonObj.data.banners[i];
                    if (banner.id == '' ) {
                        caseResult.failWithMsg("banner ID为空");
                        return ;
                        }
                }
                caseResult.setResultMsg("商品搜索结果页正确返回banner ID");
          }
    });
});