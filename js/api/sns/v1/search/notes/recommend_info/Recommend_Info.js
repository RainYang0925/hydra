
describe("test recommend info return ok...", function(){
    it("source home should return OK...",function(){
        var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
        var login = require('js/common/login_d/login_d');
        var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

        var reqURL = "http://d1.xiaohongshu.com/api/sns/v1/search/notes/recommend_info?deviceId="+phoneInfo.deviceId+"&keyword=Dior&lang=zh&platform=iOS&sid="+sessionId+"&source=home&_debug_=WHOSYOURDADDY"

        var restfulUtils = new RestfulUtils();
        restfulUtils.setServerURL(reqURL);
        restfulUtils.execute();
        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        JSLog.info(jsonObj.data);

        if (jsonObj.data==null){
            caseResult.setResultMsg("返回没有匹配内容")
        }
         for (var i=0;i<jsonObj.data.note_topics.length;i++){

                  var item =jsonObj.data.note_topics[i];

                  if (item==null)
                                  {
                                      caseResult.failWithMsg("数据空，blank");
                                  }
                              else
                                  {
                                      JSLog.info("成功返回successfully");
                                  }
                  }

           caseResult.setResultMsg(restfulUtils.responseStatusCode);


    });
    it("source explore should return OK...",function(){
            var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
            var login = require('js/common/login_d/login_d');
            var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

            var reqURL = "http://d1.xiaohongshu.com/api/sns/v1/search/notes/recommend_info?deviceId="+phoneInfo.deviceId+"&keyword=Dior&lang=zh&platform=iOS&sid="+sessionId+"&source=explore&_debug_=WHOSYOURDADDY"

            var restfulUtils = new RestfulUtils();
            restfulUtils.setServerURL(reqURL);
            restfulUtils.execute();
            var jsonObj = JSON.parse(restfulUtils.getResponseBody());
            JSLog.info(jsonObj.data);

            if (jsonObj.data==null){
                caseResult.setResultMsg("返回没有匹配内容")
            }

             for (var i=0;i<jsonObj.data.note_topics.length;i++){

                      var item =jsonObj.data.note_topics[i];

                      if (item==null)
                                      {
                                          caseResult.failWithMsg("数据空，blank");
                                      }
                                  else
                                      {
                                          JSLog.info("成功返回successfully");
                                      }
                      }

             caseResult.setResultMsg(restfulUtils.responseStatusCode);

        });

        it("source store should return OK...",function(){
                var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
                var login = require('js/common/login_d/login_d');
                var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

                var reqURL = "http://d1.xiaohongshu.com/api/sns/v1/search/notes/recommend_info?deviceId="+phoneInfo.deviceId+"&keyword=Dior&lang=zh&platform=iOS&sid="+sessionId+"&source=store&_debug_=WHOSYOURDADDY"

                var restfulUtils = new RestfulUtils();
                restfulUtils.setServerURL(reqURL);
                restfulUtils.execute();
                var jsonObj = JSON.parse(restfulUtils.getResponseBody());
                JSLog.info(jsonObj.data);

                if (jsonObj.data==null){
                    caseResult.setResultMsg("返回没有匹配内容")
                }

                for (var i=0;i<jsonObj.data.note_topics.length;i++){

                   var item =jsonObj.data.note_topics[i];

                   if (item==null)
                                   {
                                       caseResult.failWithMsg("数据空，blank");
                                   }
                               else
                                   {
                                       JSLog.info("成功返回successfully");
                                   }
                   }

                caseResult.setResultMsg(restfulUtils.responseStatusCode);

            });

            it("source recommend user should return OK...",function(){
                    var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
                    var login = require('js/common/login_d/login_d');
                    var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

                    var reqURL = "http://d1.xiaohongshu.com/api/sns/v1/search/notes/recommend_info?deviceId="+phoneInfo.deviceId+"&keyword=Dior&lang=zh&platform=iOS&sid="+sessionId+"&source=recommend_user&_debug_=WHOSYOURDADDY"

                    var restfulUtils = new RestfulUtils();
                    restfulUtils.setServerURL(reqURL);
                    restfulUtils.execute();
                    var jsonObj = JSON.parse(restfulUtils.getResponseBody());
                    JSLog.info(jsonObj.data);

                    if (jsonObj.data==null){
                        caseResult.setResultMsg("返回没有匹配内容")
                    }

                    for (var i=0;i<jsonObj.data.note_topics.length;i++){

                                    var item =jsonObj.data.note_topics[i];

                                    if (item==null)
                                                    {
                                                        caseResult.failWithMsg("数据空，blank");
                                                    }
                                                else
                                                    {
                                                        JSLog.info("成功返回successfully");
                                                    }
                                    }

                    caseResult.setResultMsg(restfulUtils.responseStatusCode);


                });

   it("negative, blank should return OK...",function(){
           var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
           var login = require('js/common/login_d/login_d');
           var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

           var reqURL = "http://d1.xiaohongshu.com/api/sns/v1/search/notes/recommend_info?deviceId="+phoneInfo.deviceId+"&keyword=%20&lang=zh&platform=iOS&sid="+sessionId+"&source=home&_debug_=WHOSYOURDADDY"

           var restfulUtils = new RestfulUtils();
           restfulUtils.setServerURL(reqURL);
           restfulUtils.execute();
           var jsonObj = JSON.parse(restfulUtils.getResponseBody());
           JSLog.info(jsonObj.data);

           if (jsonObj.data==null){
               caseResult.setResultMsg("返回没有匹配内容")
           }

           for (var i=0;i<jsonObj.data.note_topics.length;i++){

                        var item =jsonObj.data.note_topics[i];

                        if (item==null)
                                        {
                                            JSLog.info("数据空，blank");
                                        }
                                    else
                                        {
                                            JSLog.info("成功返回successfully");
                                        }
                        }

           caseResult.setResultMsg(restfulUtils.responseStatusCode);


       });
    it("negative, illegal words should return OK...",function(){
        var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
        var login = require('js/common/login_d/login_d');
        var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

        var reqURL = "http://d1.xiaohongshu.com/api/sns/v1/search/notes/recommend_info?deviceId="+phoneInfo.deviceId+"&keyword=~!%40%40%23%24%25%5E%24%23%40~&lang=zh&platform=iOS&sid="+sessionId+"&source=home&_debug_=WHOSYOURDADDY"

        var restfulUtils = new RestfulUtils();
        restfulUtils.setServerURL(reqURL);
        restfulUtils.execute();
        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        JSLog.info(jsonObj.data);

        if (jsonObj.data==null){
            caseResult.setResultMsg("返回没有匹配内容")
        }

        for (var i=0;i<jsonObj.data.note_topics.length;i++){

                     var item =jsonObj.data.note_topics[i];

                     if (item==null)
                                     {
                                         JSLog.info("数据空，blank");
                                     }
                                 else
                                     {
                                         JSLog.info("成功返回successfully");
                                     }
                     }

         caseResult.setResultMsg(restfulUtils.responseStatusCode);


    });

    it("recommend good should return OK...",function(){
            var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
            var login = require('js/common/login_d/login_d');
            var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

            var reqURL = "http://d1.xiaohongshu.com/api/sns/v1/search/notes/recommend_info?deviceId="+phoneInfo.deviceId+"&keyword=Dior&lang=zh&platform=iOS&sid="+sessionId+"&source=home&_debug_=WHOSYOURDADDY"

            var restfulUtils = new RestfulUtils();
            restfulUtils.setServerURL(reqURL);
            restfulUtils.execute();
            var jsonObj = JSON.parse(restfulUtils.getResponseBody());
            JSLog.info(jsonObj.data);

            if (jsonObj.data==null){
                caseResult.setResultMsg("返回没有匹配内容")
            }

             if (jsonObj.data.recommend_goods.id==null)

                {
                    JSLog.info("SPU空")
                }

             else

                {

                    JSLog.info("成功返回SPU")
                }

             if (jsonObj.data.recommend_poi.id==0)

                 {
                    JSLog.info("should return poi")
                 }
             else

                {

                    JSLog.info("成功返回poi")
                }
             caseResult.setResultMsg(restfulUtils.responseStatusCode);
        });

    });