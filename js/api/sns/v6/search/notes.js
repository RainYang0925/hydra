describe("test notes return ok...", function(){
    it("source home should return OK...",function(){
        var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
        var login = require('js/common/login_d/login_d');
        var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

        var reqURL = "http://d1.xiaohongshu.com/api/sns/v6/search/notes?deviceId="+phoneInfo.deviceId+"&keyword=Dior&lang=zh&platform=iOS&sid="+sessionId+"&source=home&_debug_=WHOSYOURDADDY"

        var restfulUtils = new RestfulUtils();
        restfulUtils.setServerURL(reqURL);
        restfulUtils.execute();
        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        JSLog.info(jsonObj.data);

        if (jsonObj.data==null){
            JSLog.info("返回没有匹配内容")
        }


        if (jsonObj.data.recommend_filter_positions.length>0){

            JSLog.info("return position:"+jsonObj.data.recommend_filter_positions);
        }

        for (var i=0;i<jsonObj.data.notes.length;i++){

                            var item = jsonObj.data.notes[i];

                            if (item.desc==0)
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


        it("negative, illegal words should return OK...",function(){
                var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
                var login = require('js/common/login_d/login_d');
                var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

                var reqURL = "http://d1.xiaohongshu.com/api/sns/v6/search/notes?deviceId="+phoneInfo.deviceId+"&keyword=~!%40%23%24%23%25%25%5E%25%5E%26~&lang=zh&platform=iOS&sid="+sessionId+"&source=home&_debug_=WHOSYOURDADDY"

                var restfulUtils = new RestfulUtils();
                restfulUtils.setServerURL(reqURL);
                restfulUtils.execute();
                var jsonObj = JSON.parse(restfulUtils.getResponseBody());
                JSLog.info(jsonObj.data);

                if (jsonObj.data==null){
                    JSLog.info("返回没有匹配内容")
                }

                if (jsonObj.data.recommend_filter_positions.length>0){

                            JSLog.info("return position:"+jsonObj.data.recommend_filter_positions);
                        }

                for (var i=0;i<jsonObj.data.notes.length;i++){

                                            var item = jsonObj.data.notes[i];

                                            if (item.desc==0)
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
    });
