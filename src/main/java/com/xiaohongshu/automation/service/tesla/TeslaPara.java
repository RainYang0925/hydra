package com.xiaohongshu.automation.service.tesla;

import com.xiaohongshu.automation.service.common.CommonPara;

/**
 * @author lingxue created on 4/21/16
 * @version v0.1
 **/

public class TeslaPara extends CommonPara {

    private String service;
    private String method;
    private String inclass = "";
    private String invalue = "";
    private int  testFlag = 0;
    private String ip;
    private String version = "";
    private Integer timeout;
    private boolean lurker;

    public boolean isLurker() {
        return lurker;
    }

    public void setLurker(boolean lurker) {
        this.lurker = lurker;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    private String group = "";

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getService(){
        return service;
    }

    public void setService(String service){
        this.service = service;
    }

    public String getMethod(){
        return method;
    }

    public void setMethod(String method){
        this.method = method;
    }

    public String getInclass(){
        return inclass;
    }

    public void setInclass(String inclass){
        this.inclass = inclass;
    }

    public String getInvalue(){
        return invalue;
    }

    public void setInvalue(String invalue){
        this.invalue = invalue;
    }

    public String getIp(){
        return ip;
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public int getTestFlag(){
        return testFlag;
    }

    public void setTestFlag(int testFlag){
        this.testFlag = testFlag;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

}
