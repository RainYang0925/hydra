
exports.login = function (phoneNumber,deviceId){
        var restfulUtils = new RestfulUtils();
        var mobileTokenURL = "http://sit.test.xiaohongshu.com/api/v1/sms/check_code?code=1024" ;
        mobileTokenURL+="&deviceId="+deviceId+"&platform=iOS&type=phone&zone=86" ;
        restfulUtils.setServerURL(mobileTokenURL+"&phone="+phoneNumber);
        restfulUtils.setHttpMethod("get");

        restfulUtils.execute();
        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        var mobileToken = jsonObj.data.token;
        var loginURL = "http://sit.test.xiaohongshu.com/api/sns/v1/user/login" ;
        var reqStr = "deviceId="+deviceId+"A&lang=zh&mobile_token="+mobileToken+"&phone="+phoneNumber+"&platform=iOS&type=phone&zone=86";

        JSLog.info('will login.... [Arthur.Ren]');

        restfulUtils.setServerURL(loginURL);
        restfulUtils.setHttpMethod("post");
        restfulUtils.setRequestData(reqStr+"&_debug_=WHOSYOURDADDY");

        restfulUtils.execute();
        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        JSLog.info(jsonObj.data.sessionid);
        return jsonObj.data.sessionid;
    }


