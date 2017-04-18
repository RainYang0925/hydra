var targetURL = "http://sit.test.xiaohongshu.com/api/sns/v4/homefeed?deviceId=14193E96-0D20-4DC1-9FBA-94919586A060&lang=zh&num=20&oid=homefeed_recommend&page=1&platform=iOS&sid=session.1174056819276511572&sign=58a2be217eba330e1f79c3a8b8940b85&size=l&t=1489647413&value=simple"

//&sid=session.1174056819276511572&sign=58a2be217eba330e1f79c3a8b8940b85&size=l&t=1489647413&value=simple

var postsURL = "http://localhost:3003/posts";

describe("The suite: testcase_01_01",function(){

    it("test api v4/homefeed, result should not be null ",function(){

        var restfulUtils = new RestfulUtils();
        restfulUtils.setServerURL(targetURL);
        restfulUtils.setHttpMethod("get");

        restfulUtils.execute();
        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        //JSLog.info(jsonObj);
        //Assert.assertTrue(jsonObj.data.length>0);
        if (jsonObj.data.length<=0){
            caseResult.fail();
        }
    });



});


/**
  Given test api v4/homefeed,
  When get http://sit.test.xiaohongshu.com/api/xxxx
  Then we should be able to see the result contains "xxx" */
