
describe("获取商品筛选标签 /api/store/ps/tags", function(){

    it("keyword=香水 return OK ", function(){

        var restfulUtils = new RestfulUtils();
        var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+ '/12000000009.json'));
        var login = require('js/common/login_t/login_t.js');
        var sessionId = login.login(phoneInfo.phone, phoneInfo.deviceId);

        var reqURL = "http://t1.b.xiaohongshu.com/api/store/ps/tags?keyword=香水&session="+ sessionId+ "&deviceId="+ phoneInfo.deviceId;
        restfulUtils.setServerURL(reqURL+ "&_debug_=WHOSYOURDADDY");
        restfulUtils.setHttpMethod("get");
        restfulUtils.execute();

        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        expect(jsonObj.result).toBe(0);

        var filter_groupsAmount = jsonObj.data.filter_groups.length;
        if(filter_groupsAmount == 0){
           caseResult.failWithMsg("获取商品筛选标签为空");
          }
          else{
                for(var i= 0; i< filter_groupsAmount; i++){
                    var filter_groups= jsonObj.data.filter_groups[i];
                    var filter_tagsAmount = filter_groups.filter_tags.length;
                    for(var j= 0; j< filter_tagsAmount; j++){
                         var filter_tags = filter_groups.filter_tags[j];
                         if (filter_tags.id == '' ) {
                             caseResult.failWithMsg("筛选标签 ID为空");
                             return ;
                            }
                    }
                }
                caseResult.setResultMsg("商品搜索结果页获取商品筛选标签正确返回标签 ID");
          }
    });
});