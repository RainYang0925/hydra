package com.xiaohongshu.automation.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by zren on 17/3/8.
 */
public class TestJsonSchema {


    @Test
    public void testJSONSchema() throws IOException, ProcessingException {
        JsonValidator validator = JsonSchemaFactory.byDefault().getValidator();

        String schemaStr= "{" +
                "   \"properties\":{" +
                "       \"name\":{\"type\":\"string\"}" +
                "}}";
        System.out.println(JSON.parse(schemaStr).toString());
        JsonNode schema = JsonLoader.fromString(schemaStr);
        System.out.println(schema.asText());
        String objStr = "{\"sname2\":\"asdsd\"}";
        JsonNode jsonObj = JsonLoader.fromString(objStr);
        System.out.println(jsonObj.toString());
        ProcessingReport pr = validator.validate(schema, jsonObj,true);

        Assert.assertTrue(pr.isSuccess());
    }
}
