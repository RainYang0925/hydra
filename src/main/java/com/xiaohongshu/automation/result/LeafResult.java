package com.xiaohongshu.automation.result;

import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @author lingxue created on 6/28/16
 * @version v0.1
 **/

public class LeafResult extends AbstractResult{

    public LeafResult(String name){
        super(name);
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public String getResult() {
        return result?"成功":"失败";
    }

    public void methodNotSupport(String methodName){
        LoggerFactory.getLogger(LeafResult.class).error("Method: " +  methodName + " not implement in class LeafResult");
    }

    @Override
    public void addResult(AbstractResult result) {
        methodNotSupport(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Override
    public ArrayList<AbstractResult> getChildResults() {
        methodNotSupport(Thread.currentThread().getStackTrace()[1].getMethodName());
        return null;
    }

    @Override
    public int getChildNums() {
        methodNotSupport(Thread.currentThread().getStackTrace()[1].getMethodName());
        return 0;
    }

    @Override
    public int getFailResults() {
        methodNotSupport(Thread.currentThread().getStackTrace()[1].getMethodName());
        return 0;
    }

    @Override
    public int getPassResults() {
        methodNotSupport(Thread.currentThread().getStackTrace()[1].getMethodName());
        return 0;
    }

    @Override
    public String getSuccessRate() {
        methodNotSupport(Thread.currentThread().getStackTrace()[1].getMethodName());
        return null;
    }

    @Override
    public int getAllChildNums() {
        methodNotSupport(Thread.currentThread().getStackTrace()[1].getMethodName());
        return 0;
    }

    @Override
    public int getAllPassResults() {
        methodNotSupport(Thread.currentThread().getStackTrace()[1].getMethodName());
        return 0;
    }

    @Override
    public int getAllFailResults() {
        methodNotSupport(Thread.currentThread().getStackTrace()[1].getMethodName());
        return 0;
    }

    @Override
    public String getAllSuccssRate() {
        methodNotSupport(Thread.currentThread().getStackTrace()[1].getMethodName());
        return null;
    }

    public static void main(String[] args){
        AbstractResult result = new LeafResult("a");
        result.getChildNums();
    }
}
