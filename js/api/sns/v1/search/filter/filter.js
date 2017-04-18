describe("test note filter return ok...", function(){
    it(" should return OK...",function(){
        var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
        var login = require('js/common/login_d/login_d');
        var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

        var reqURL = "http://d1.xiaohongshu.com/api/sns/v1/search/filter?deviceId="+phoneInfo.deviceId+"&keyword=Dior&lang=zh&platform=iOS&sid="+sessionId+"&_debug_=WHOSYOURDADDY"

        var restfulUtils = new RestfulUtils();
        restfulUtils.setServerURL(reqURL);
        restfulUtils.execute();
        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        JSLog.info(jsonObj.data);

        if (jsonObj.data==null){
            caseResult.setResultMsg("返回没有匹配内容")
        }

        for (var i=0;i<jsonObj.data.filter_groups.length;i++)
        {

            for (var j=0; j<jsonObj.data.filter_groups[i].filter_tags.length;j++)
            {

                    var item = jsonObj.data.filter_groups[i].filter_tags[j];

                    if (item.id.length == 0)
                    {
                            JSLog.info("数据空，blank");
                    }
                    else
                    {
                            JSLog.info("成功返回successfully");
                    }
            }

            caseResult.setResultMsg(restfulUtils.responseStatusCode);

        }

    });

    it("negative, blank should return OK...",function(){
            var phoneInfo = JSON.parse(JsonSchemaUtils.getJsonFile(PWD+'/12979797979.json'));
            var login = require('js/common/login_d/login_d');
            var sessionId = login.login(phoneInfo.phone,phoneInfo.deviceId);

            var reqURL = "http://d1.xiaohongshu.com/api/sns/v1/search/filter?deviceId="+phoneInfo.deviceId+"&keyword=%20&lang=zh&platform=iOS&sid="+sessionId+"&_debug_=WHOSYOURDADDY"

            var restfulUtils = new RestfulUtils();
            restfulUtils.setServerURL(reqURL);
            restfulUtils.execute();
            var jsonObj = JSON.parse(restfulUtils.getResponseBody());
            JSLog.info(jsonObj.data);

            if (jsonObj.data==null){
                JSLog.info("返回没有匹配内容")
            }


            if (jsonObj.data.filter_groups.length == 0)

                JSLog.info("数据空，blank");


            for (var i=0;i<jsonObj.data.filter_groups.length;i++){

                                var item = jsonObj.data.filter_groups[i];

                                if (item.length == 0)
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

                var reqURL = "http://d1.xiaohongshu.com/api/sns/v1/search/filter?deviceId="+phoneInfo.deviceId+"&keyword=~!%40%23%24%23%25%25%5E%25%5E%26~&lang=zh&platform=iOS&sid="+sessionId+"&_debug_=WHOSYOURDADDY"

                var restfulUtils = new RestfulUtils();
                restfulUtils.setServerURL(reqURL);
                restfulUtils.execute();
                var jsonObj = JSON.parse(restfulUtils.getResponseBody());
                JSLog.info(jsonObj.data);

                if (jsonObj.data==null){
                    caseResult.setResultMsg("返回没有匹配内容")
                }

                for (var i=0;i<jsonObj.data.filter_groups.length;i++){

                            for (var j=0; j<jsonObj.data.filter_groups[i].filter_tags.length;j++){

                                    var item = jsonObj.data.filter_groups[i].filter_tags[j];

                                    if (item.id== '0')
                                        {
                                            JSLog.info("数据空，blank");
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
