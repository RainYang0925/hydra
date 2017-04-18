package com.xiaohongshu.automation.service.common;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xiaohongshu.automation.utils.FileUtils;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;

/**
 * @author lingxue created on 6/21/16
 * @version v0.1
 * 公共服务类,其他的测试服务类需要由此继承
 **/

public abstract class CommonService {

    protected CommonPara para;
    protected Object response;

    /**
     * 设置参数文件路径
     * @param path 文件的路径
     */
    public void setDataFile(String path){
        File file = new File(path);
        if(file.exists()){
            try{
                LoggerFactory.getLogger(CommonService.class).info("Data File Path: " + file.getAbsolutePath());
                setDataJson(FileUtils.readStringFromFile(file));
            }
            catch (IOException e){
                LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
                e.printStackTrace();
            }
        }
        else{
            LoggerFactory.getLogger(this.getClass()).error("Data File:" + path + " not exist");
        }
    }

    /**
     * 设置参数
     * @param json 参数的json
     */
    public void setDataJson(String json){
        try {
            para = JSONObject.parseObject(json, para.getClass());
        }
        catch (JSONException e){
            LoggerFactory.getLogger(this.getClass()).error("Parse Json Fail: " + json);
            e.printStackTrace();
        }
    }

    /**
     * 获取测试的返回结果
     * @return 返回结果的String
     */
    public String getResponse(){
        return JSONObject.toJSONString(response, SerializerFeature.WriteNonStringKeyAsString);
    }

    /**
     * 执行测试
     * @return 返回执行结果,true为成功,false为失败
     */
    abstract public boolean execute();
}
