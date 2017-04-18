
var schema = {
    "required" : ["id"],
    "properties" : {
        "id": {"type":"integer"},
        "title": {"type":"string"},
        "author": {"type":"string"}
    }
}

describe("The suite: testcase_01_01",function(){

    it("The spec: test rest api:",function(){

        var restfulUtils = new RestfulUtils();
        restfulUtils.setServerURL("http://localhost:3000/posts");
        restfulUtils.setHttpMethod("get");

        restfulUtils.execute();
        var jsonObj = JSON.parse(restfulUtils.getResponseBody());
        JSLog.info(jsonObj);
    });


    it("The spec: check schema",function(){
        var schemaStr = JSON.stringify(schema);
        importPackage("com.fasterxml.jackson.databind");
        importPackage("com.github.fge.jackson");
        importPackage("com.github.fge.jsonschema.main");
        System.out.println(PWD);
        var schemaNode = JsonLoader.fromFile(new File(PWD+'/schema.json'));
        var obj = {id: 20};
        var objNode = JsonLoader.fromString(JSON.stringify(obj));
        var validator = JsonSchemaFactory.byDefault().getValidator();
        var pr = validator.validate(schemaNode,objNode);
        expect(pr.isSuccess()).toBe(true);
    })

});