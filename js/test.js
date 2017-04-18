describe("This is the suite: Just test xiaohongshu can open", function() {
  var a;

  it("This is a case: Let's open the chrome and navigate to www.xiaohongshu.com ", function() {
    a = true;
    expect(a).toBe(true);
    importPackage('com.xiaohongshu.automation.service.webdriver');
    importPackage('com.xiaohongshu.automation.service.webdriver.common');
    //importClass('com.xiaohongshu.automation.service.webdriver.common.PageObject');
    var p = new DefaultPageObject('http://www.xiaohongshu.com',null);
    try {
        //p.load();
        expect(p.get)
        caseResult.setResultMsg('page loaded!')
    }catch(err){
        err.printStackTrace();
    }
    //caseResult.setResultMsg('succeed!');
  });


  it("This is another case: test simple rest api", function(){
    var restfulUtils = new RestfulUtils();
    restfulUtils.setServerURL("http://localhost:3000/posts");
    //restfulUtils.setBinaryFile("/Users/zhanghao/Documents/Work/test/test.zip");
    restfulUtils.setHttpMethod("get");
    restfulUtils.execute();
    JSLog.info(restfulUtils.getResponseBody());
  });
});
