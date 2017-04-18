describe("This is the suite: Just test xiaohongshu can open", function() {
  var appKey = "fc8d52972b64e3bb" ;
  it("This is another case: test simple rest api", function(){
    var restfulUtils = new RestfulUtils();
    var url = "http://api.jisuapi.com/iqa/query?appkey=" + appKey + "&question=上海人口" ;
    restfulUtils.setServerURL(url);
    restfulUtils.setHttpMethod("get");
    restfulUtils.execute();
    JSLog.info(restfulUtils.getResponseBody());
  });
});
