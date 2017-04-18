package com.xiaohongshu.automation.runner;

import com.xiaohongshu.automation.result.ResultListenAdapterForTestNG;
import org.slf4j.LoggerFactory;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

/**
 * @author lingxue created on 6/28/16
 * @version v0.1
 **/

public class JavaTestSuiteRunner extends TestSuiteRunner {

    public JavaTestSuiteRunner(String suiteName) {
        super(suiteName);
    }

    @Override
    public boolean isScriptSuite() {
        return false;
    }

    @Override
    public void runSuiteConcrete() {
        String className = suiteName.substring(5);
        try {
            TestListenerAdapter testListenerAdapter = new ResultListenAdapterForTestNG(suiteResult);
            TestNG testNG = new TestNG();
            testNG.setTestClasses(new Class[]{Class.forName(className)});
            testNG.addListener(testListenerAdapter);
            testNG.run();
        }
        catch (ClassNotFoundException e){
            TestSuiteRunner.suiteResult.failWithMsg(e.getMessage());
            LoggerFactory.getLogger(JavaTestSuiteRunner.class).error("Test Class not found: " + className);
        }
        catch (Exception e){
            TestSuiteRunner.suiteResult.failWithMsg(e.getMessage());
            LoggerFactory.getLogger(JavaTestSuiteRunner.class).error("Execute suite fail: " + className);
            e.printStackTrace();
        }
    }
}
