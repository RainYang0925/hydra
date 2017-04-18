package com.xiaohongshu.automation.result;

import java.util.ArrayList;
/**
 * @author lingxue created on 6/28/16
 * @version v0.1
 **/

public class CompositeResult extends AbstractResult {

    private ArrayList<AbstractResult> childResults = new ArrayList<AbstractResult>();
    private int failResults = 0;

    public CompositeResult(String name) {
        super(name);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public String getResult() {
        return result?"测试执行结束":"测试集脚本发生错误";
    }

    @Override
    public void addResult(AbstractResult result) {
        if(result != null){
            childResults.add(result);
            if(!result.isPass()){
                failResults++;
            }
        }

    }

    @Override
    public ArrayList<AbstractResult> getChildResults() {
        return childResults;
    }

    @Override
    public int getChildNums(){
        return childResults.size();
    }

    @Override
    public int getFailResults(){
        return failResults;
    }

    @Override
    public int getPassResults(){
        return getChildNums() - getFailResults();
    }

    @Override
    public String getSuccessRate(){
        return ResultCalculator.getPercentageRate(getPassResults(),getChildNums());
    }

    @Override
    public int getAllChildNums() {
        int count = 0;
        for(AbstractResult result : childResults){
            count += result.getChildNums();
        }
        return count;
    }

    @Override
    public int getAllPassResults() {
        int count = 0;
        for(AbstractResult result : childResults){
            count += result.getPassResults();
        }
        return count;
    }

    @Override
    public int getAllFailResults() {
        int count = 0;
        for(AbstractResult result : childResults){
            count += result.getFailResults();
        }
        return count;
    }

    @Override
    public String getAllSuccssRate() {
        return ResultCalculator.getPercentageRate(getAllPassResults(),getAllChildNums());
    }
}
