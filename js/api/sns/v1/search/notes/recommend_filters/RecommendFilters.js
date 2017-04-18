describe("test recommend filters return ok...", function(){
    it("source home should return OK...",function(){
        var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
        var login = require('js/common/login_d/login_d');
        var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

        var reqURL = "http://d1.xiaohongshu.com/api/sns/v1/search/notes/recommend_filters?deviceId="+phoneInfo.deviceId+"&keyword=Dior&lang=zh&platform=iOS&sid="+sessionId+"&source=home&_debug_=WHOSYOURDADDY"

        var restfulUtils = new RestfulUtils();
        restfulUtils.setServerURL(reqURL);
        restfulUtils.execute();
        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        JSLog.info(jsonObj.data);

        if (jsonObj.data.length<=0){
            JSLog.info("返回没有匹配内容");
        }

        for (var i=0;i<jsonObj.data.length;i++){

            for (var j=0;j<jsonObj.data[i].recommend_filters.length;j++){

                var item = jsonObj.data[i].recommend_filters[j];

                   if (item.name==0)
                       {
                           caseResult.failWithMsg("数据空，blank");
                       }
                   else
                       {
                           JSLog.info("成功返回successfully");
                       }
               }

         }

         caseResult.setResultMsg(restfulUtils.responseStatusCode);


    });


            it("source explore should return OK...",function(){
                var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
                var login = require('js/common/login_d/login_d');
                var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

                var reqURL = "http://d1.xiaohongshu.com/api/sns/v1/search/notes/recommend_filters?deviceId="+phoneInfo.deviceId+"&keyword=Dior&lang=zh&platform=iOS&sid="+sessionId+"&source=explore&_debug_=WHOSYOURDADDY"

                var restfulUtils = new RestfulUtils();
                restfulUtils.setServerURL(reqURL);
                restfulUtils.execute();
                var jsonObj = JSON.parse(restfulUtils.getResponseBody());
                JSLog.info(jsonObj.data);

                if (jsonObj.data.length<=0){
                    JSLog.info("返回没有匹配内容");
                }

                for (var i=0;i<jsonObj.data.length;i++){

                            for (var j=0;j<jsonObj.data[i].recommend_filters.length;j++){

                                var item = jsonObj.data[i].recommend_filters[j];

                                   if (item.name==0)
                                       {
                                           caseResult.failWithMsg("数据空，blank");
                                       }
                                   else
                                       {
                                           JSLog.info("成功返回successfully");
                                       }
                               }

                         }

                caseResult.setResultMsg(restfulUtils.responseStatusCode);


            });


         it("source store should return OK...",function(){
             var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
             var login = require('js/common/login_d/login_d');
             var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

             var reqURL = "http://d1.xiaohongshu.com/api/sns/v1/search/notes/recommend_filters?deviceId="+phoneInfo.deviceId+"&keyword=Dior&lang=zh&platform=iOS&sid="+sessionId+"&source=store&_debug_=WHOSYOURDADDY"

             var restfulUtils = new RestfulUtils();
             restfulUtils.setServerURL(reqURL);
             restfulUtils.execute();
             var jsonObj = JSON.parse(restfulUtils.getResponseBody());
             JSLog.info(jsonObj.data);

             if (jsonObj.data.length<=0){
                 JSLog.info("返回没有匹配内容")
             }

             for (var i=0;i<jsonObj.data.length;i++){

                         for (var j=0;j<jsonObj.data[i].recommend_filters.length;j++){

                             var item = jsonObj.data[i].recommend_filters[j];

                                if (item.name==0)
                                    {
                                        caseResult.failWithMsg("数据空，blank");
                                    }
                                else
                                    {
                                        JSLog.info("成功返回successfully");
                                    }
                            }

                      }
             caseResult.setResultMsg(restfulUtils.responseStatusCode);
         });


         it("source recommend user should return OK...",function(){
             var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
             var login = require('js/common/login_d/login_d');
             var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

             var reqURL = "http://d1.xiaohongshu.com/api/sns/v1/search/notes/recommend_filters?deviceId="+phoneInfo.deviceId+"&keyword=Dior&lang=zh&platform=iOS&sid="+sessionId+"&source=recommend_user&_debug_=WHOSYOURDADDY"

             var restfulUtils = new RestfulUtils();
             restfulUtils.setServerURL(reqURL);
             restfulUtils.execute();
             var jsonObj = JSON.parse(restfulUtils.getResponseBody());
             JSLog.info(jsonObj.data);

             if (jsonObj.data.length<=0){
                 caseResult.setResultMsg("返回没有匹配内容")
             }

//             for (var i=0;i<jsonObj.data.recommend_filters.length;i++){
//
//                                var item = jsonObj.data.recommend_filters[i];
//
//                                if (item<=0)
//                                    {
//                                        caseResult.failWithMsg("数据空，blank");
//                                    }
//                                else
//                                    {
//                                        JSLog.info("成功返回successfully");
//                                    }
//                            }
            caseResult.setResultMsg(restfulUtils.responseStatusCode)
         });



    it("negative, illegal words should return OK...",function(){
            var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
            var login = require('js/common/login_d/login_d');
            var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

            var reqURL = "http://d1.xiaohongshu.com/api/sns/v1/search/notes/recommend_filters?deviceId="+phoneInfo.deviceId+"&keyword=~!%40%23%24%23%25%25%5E%25%5E%26~&lang=zh&platform=iOS&sid="+sessionId+"&source=home&_debug_=WHOSYOURDADDY"

            var restfulUtils = new RestfulUtils();
            restfulUtils.setServerURL(reqURL);
            restfulUtils.execute();
            var jsonObj = JSON.parse(restfulUtils.getResponseBody());
            JSLog.info(jsonObj.data);

            if (jsonObj.data.length<=0){
                JSLog.info("返回没有匹配内容")
            }

            for (var i=0;i<jsonObj.data.length;i++){

                        for (var j=0;j<jsonObj.data[i].recommend_filters.length;j++){

                            var item = jsonObj.data[i].recommend_filters[j];

                               if (item.name==0)
                                   {
                                       caseResult.failWithMsg("数据空，blank");
                                   }
                               else
                                   {
                                       JSLog.info("成功返回successfully");
                                   }
                           }

                     }


             caseResult.setResultMsg(restfulUtils.responseStatusCode);


        });

        });