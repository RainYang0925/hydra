package com.xiaohongshu.automation.result;

import com.xiaohongshu.automation.utils.DateUtils;

import java.util.ArrayList;

/**
 * @author lingxue created on 6/28/16
 * @version v0.1
 **/

public abstract class AbstractResult {

    protected String name;
    protected boolean result;
    protected String resultMsg;
    protected DateUtils time;

    public AbstractResult(String name){
        this.name = name;
        result = true;
        resultMsg = "";
        time = new DateUtils();
    }

    public String getName(){
       return name;
    }

    public boolean isPass(){
        return result;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getResultMsg(){
        return resultMsg;
    }

    public DateUtils getTime(){
        return time;
    }

    public void fail(){
        result = false;
    }

    public void failWithMsg(String msg){
        fail();
        resultMsg = msg;
    }

    public void testEnd(){
        time.setEndTime();
    }

    abstract public boolean isLeaf();

    abstract public String getResult();

    abstract public void addResult(AbstractResult result);

    abstract public ArrayList<AbstractResult> getChildResults();

    abstract  public int getChildNums();

    abstract public int getFailResults();

    abstract public int getPassResults();

    abstract public String getSuccessRate();

    abstract public int getAllChildNums();

    abstract public int getAllPassResults();

    abstract public int getAllFailResults();

    abstract public String getAllSuccssRate();
}
