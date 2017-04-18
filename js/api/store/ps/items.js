
describe("根据关键词搜索商品 /api/store/ps/items", function(){

    it("mode＝word_search & sort＝ & source ＝store return OK ", function(){

        var restfulUtils = new RestfulUtils();
        var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+ '/12000000009.json'));
        var login = require('js/common/login_t/login_t.js');
        var sessionId = login.login(phoneInfo.phone, phoneInfo.deviceId);

        var reqURL = "http://t1.b.xiaohongshu.com/api/store/ps/items?page_size=20&keyword=香水&mode=word_search&sort=general&source=store&session="+ sessionId+ "&deviceId="+ phoneInfo.deviceId;
        restfulUtils.setServerURL(reqURL+ "&_debug_=WHOSYOURDADDY");
        restfulUtils.setHttpMethod("get");
        restfulUtils.execute();

        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        expect(jsonObj.result).toBe(0);

        var itemsAmount = jsonObj.data.items.length;
        if(itemsAmount == 0){
           caseResult.failWithMsg("搜索结果为空");
          }else{
                for(var i= 0; i< itemsAmount; i++){
                    var item= jsonObj.data.items[i];
                    if (item.id == '' ) {
                        caseResult.failWithMsg("商品ID为空");
                        return ;
                        }
                }
                caseResult.setResultMsg("商品搜索结果页正确返回商品ID");
          }
    });

    it("mode＝auto-complete & sort＝price & direction＝asc & source＝home return OK ", function(){

            var restfulUtils = new RestfulUtils();
            var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+ '/12000000009.json'));
            var login = require('js/common/login_t/login_t.js');
            var sessionId = login.login(phoneInfo.phone, phoneInfo.deviceId);

            var reqURL = "http://t1.b.xiaohongshu.com/api/store/ps/items?page_size=20&keyword=香水&mode=auto-complete&sort=price&direction=asc&source=home&session="+ sessionId+ "&deviceId="+ phoneInfo.deviceId;
            restfulUtils.setServerURL(reqURL+ "&_debug_=WHOSYOURDADDY");
            restfulUtils.setHttpMethod("get");
            restfulUtils.execute();

            var jsonObj = JSON.parse(restfulUtils.getResponseBody());
            expect(jsonObj.result).toBe(0);

            var itemsAmount = jsonObj.data.items.length;
            if(itemsAmount == 0){
               caseResult.failWithMsg("搜索结果为空");
              }else{
                    for(var i= 0; i< itemsAmount; i++){
                        var item= jsonObj.data.items[i];
                        if (item.id == '' ) {
                            caseResult.failWithMsg("商品ID为空");
                            return ;
                        }
                    }
                    caseResult.setResultMsg("商品搜索结果页正确返回商品ID");
              }
        });

        it("mode＝word_search & sort＝price & direction＝desc & source＝explore return OK ", function(){

                    var restfulUtils = new RestfulUtils();
                    var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+ '/12000000009.json'));
                    var login = require('js/common/login_t/login_t.js');
                    var sessionId = login.login(phoneInfo.phone, phoneInfo.deviceId);

                    var reqURL = "http://t1.b.xiaohongshu.com/api/store/ps/items?page_size=20&keyword=香水&mode=word_search&sort=price&direction=desc&source=explore&session="+ sessionId+ "&deviceId="+ phoneInfo.deviceId;
                    restfulUtils.setServerURL(reqURL+ "&_debug_=WHOSYOURDADDY");
                    restfulUtils.setHttpMethod("get");
                    restfulUtils.execute();

                    var jsonObj = JSON.parse(restfulUtils.getResponseBody());
                    expect(jsonObj.result).toBe(0);

                    var itemsAmount = jsonObj.data.items.length;
                    if(itemsAmount == 0){
                        caseResult.failWithMsg("搜索结果为空");
                      }else{
                            for(var i= 0; i< itemsAmount; i++){
                                var item= jsonObj.data.items[i];
                                if (item.id == '' ) {
                                    caseResult.failWithMsg("商品ID为空");
                                    return ;
                                    }
                                }
                            caseResult.setResultMsg("商品搜索结果页正确返回商品ID");
                           }
                });

    it("mode＝auto-complete & sort＝price & direction＝asc and source＝recommend_user return OK ", function(){

                var restfulUtils = new RestfulUtils();
                var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+ '/12000000009.json'));
                var login = require('js/common/login_t/login_t.js');
                var sessionId = login.login(phoneInfo.phone, phoneInfo.deviceId);

                var reqURL = "http://t1.b.xiaohongshu.com/api/store/ps/items?page_size=20&keyword=小绿瓶&mode=auto-complete&sort=price&direction=asc&source=recommend_user&session="+ sessionId+ "&deviceId="+ phoneInfo.deviceId;
                restfulUtils.setServerURL(reqURL+ "&_debug_=WHOSYOURDADDY");
                restfulUtils.setHttpMethod("get");
                restfulUtils.execute();

                var jsonObj = JSON.parse(restfulUtils.getResponseBody());
                expect(jsonObj.result).toBe(0);

                var itemsAmount = jsonObj.data.items.length;
                if(itemsAmount == 0){
                    caseResult.failWithMsg("搜索结果为空");
                }else{
                    for(var i= 0; i< itemsAmount; i++){
                         var item= jsonObj.data.items[i];
                            if (item.id == '' ) {
                                caseResult.failWithMsg("商品ID为空");
                                return ;
                            }
                    }
                    caseResult.setResultMsg("商品搜索结果页正确返回商品ID");
                }
            });
});