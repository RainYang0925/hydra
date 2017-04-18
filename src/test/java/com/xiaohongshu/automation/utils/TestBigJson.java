package com.xiaohongshu.automation.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import org.junit.Test;

import java.io.*;

import static org.python.bouncycastle.asn1.x500.style.RFC4519Style.o;

/**
 * Created by huaishu on 17/3/28.
 */
public class TestBigJson {

    @Test
    public void testBigJson(){
        try {
            JSONReader jsonReader = new JSONReader(new FileReader("/Users/huaishu/Desktop/test/HQing.chlsj"));
            JSONArray array = (JSONArray) jsonReader.readObject();
            int count =0 ;
            for (int i=0;i<array.size();i++){
                JSONObject record = (JSONObject) array.get(i);
                String path = record.getString("path");
                String method = record.getString("method");
                if (null!=path && path.equals("/api/collect") && method.equalsIgnoreCase("post")){
                    JSONObject req = record.getJSONObject("request").getJSONObject("body");
                    String reqStr = req.toJSONString();
                    if (reqStr.indexOf("Share_WeixinSessiontouchUpInside")>0 || reqStr.indexOf("Share_WeixinTimelinetouchUpInside")>0 || reqStr.indexOf("Share_SinaWeibotouchUpInside")>0 || reqStr.indexOf("Share_QQtouchUpInside")>0 || reqStr.indexOf("Share_Cover_SnapShottouchUpInside")>0 || reqStr.indexOf("Share_SnapShottouchUpInside")>0 || reqStr.indexOf("Share_CopyLinktouchUpInside")>0){
                        String text = req.getString("text");
                        JSONObject textObj = JSON.parseObject(text);
                        JSONArray dataArr = textObj.getJSONArray("data");
                        for (int dI =0;dI< dataArr.size();dI++){
                            JSONObject item = dataArr.getJSONObject(dI);
                                String se_ac = item.getString("se_ac");
                                if (null != se_ac)
                                if (se_ac.equals("Share_WeixinSessiontouchUpInside") || se_ac.equals("Share_WeixinTimelinetouchUpInside") || se_ac.equals("Share_SinaWeibotouchUpInside") || se_ac.equals("Share_QQtouchUpInside") || se_ac.equals("Share_Cover_SnapShottouchUpInside") || se_ac.equals("Share_SnapShottouchUpInside") || se_ac.equals("Share_CopyLinktouchUpInside")){
                                    count++;
                                }
                        }
                    }
                }
            }
            System.out.println(count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
