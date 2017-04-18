package com.xiaohongshu.automation.utils;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by zren on 17/3/28.
 */
public class TestBase64Decoder {
    @Test
    public void testB64De(){
        try {
            JSONObject req = JSONObject.parseObject(FileUtils.readStringFromFile(new File("/tmp/req.json")));
            System.out.println(req.toJSONString());
            String path = req.getString("path");
            System.out.println(path);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
