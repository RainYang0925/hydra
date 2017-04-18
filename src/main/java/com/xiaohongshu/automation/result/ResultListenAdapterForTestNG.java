package com.xiaohongshu.automation.result;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * @author lingxue created on 6/29/16
 * @version v0.1
 **/

public class ResultListenAdapterForTestNG extends TestListenerAdapter {

    private CompositeResult suiteResult;
    private LeafResult caseResult;

    public ResultListenAdapterForTestNG(CompositeResult suiteResult){
        this.suiteResult = suiteResult;
    }

    @Override
    public void onTestStart(ITestResult result) {
        caseResult = new LeafResult(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        caseResult.testEnd();
        suiteResult.addResult(caseResult);
    }

        @Override
    public void onTestFailure(ITestResult tr) {
        caseResult.testEnd();
        caseResult.failWithMsg(tr.getThrowable().getMessage());
        suiteResult.addResult(caseResult);
    }

}
