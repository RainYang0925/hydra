var targetURL = "http://sit.test.xiaohongshu.com/api/sns/v4/homefeed?deviceId=14193E96-0D20-4DC1-9FBA-94919586A060&lang=zh&num=20&oid=homefeed_recommend&page=1&platform=iOS&sid=session.1174056819276511572&sign=58a2be217eba330e1f79c3a8b8940b85&size=l&t=1489647413&value=simple"

//&sid=session.1174056819276511572&sign=58a2be217eba330e1f79c3a8b8940b85&size=l&t=1489647413&value=simple

var postsURL = "http://localhost:3003/posts";

describe("The suite: testcase_01_02",function(){

    it("test api localhost:3003/posts",function(){
        var restfulUtils = new RestfulUtils();
        var reqStr = "{ "+
                      "\"id\": 4,"+
                      "\"title\": \"json-server3\","+
                      "\"author\": \"typicode\""+
                      "}";
        restfulUtils.setServerURL(postsURL);
        restfulUtils.setHttpMethod("post");
        restfulUtils.setRequestDataFile(PWD + "/post.json");
        restfulUtils.execute();
        var jsonObj = JSON.parse(restfulUtils.getResponseBody());

    });
});