package com.xiaohongshu.automation.runner;

import com.xiaohongshu.automation.config.GenericConfig;
import com.xiaohongshu.automation.result.CompositeResult;
import com.xiaohongshu.automation.utils.FileUtils;
import org.slf4j.LoggerFactory;

import java.io.File;
/**
 * @author lingxue created on 6/22/16
 * @version v0.1
 * 测试集执行器抽象父类,所有的测试集执行器都应从此类继承
 **/

public abstract class TestSuiteRunner {

    protected String suitePath;
    protected String suiteName;
    static protected CompositeResult suiteResult = null;

    public TestSuiteRunner(){}

    public TestSuiteRunner(String suiteName) {
        this.suiteName = suiteName;
        this.suitePath = GenericConfig.INSTANCE.getCasehome() + suiteName;
    }

    /**
     * 判断测试集是否应该被执行
     * @return true,应该被执行;false,不应该被执行
     */
    public static boolean suiteShouldBeRun(String suiteName){
        if(suiteName.trim().length() == 0){
            return false;
        }
        for(String exclude : FileUtils.LINE_EXCLUDE){
            if(suiteName.trim().startsWith(exclude)){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断测试集是否是脚本,从而是否需要判断存在与否
     * @return
     */
    public boolean isScriptSuite(){
        return true;
    }

    /**
     * 跑一个测试集
     * @return
     */
    final public CompositeResult runSuite() {
        LoggerFactory.getLogger(TestSuiteRunner.class).info("--------------Running Suite: " + suiteName + "-----------");
        TestSuiteRunner.suiteResult = new CompositeResult(suiteName);
        if(isScriptSuite()) {
            if (new File(suitePath).exists()) {
                runSuiteConcrete();
            } else {
                LoggerFactory.getLogger(TestSuiteRunner.class).error("Suite File not Exist: " + suitePath);
                TestSuiteRunner.suiteResult.failWithMsg("Suite File not Exist: " + suiteName);
            }
        }
        else{
            runSuiteConcrete();
        }
        TestSuiteRunner.suiteResult.testEnd();

        return TestSuiteRunner.suiteResult;
    }

    /**
     * 跑测试集具体的步骤,子类应该复写此方法
     */
    abstract public void runSuiteConcrete();
}
