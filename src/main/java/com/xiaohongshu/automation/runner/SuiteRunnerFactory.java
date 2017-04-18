package com.xiaohongshu.automation.runner;

import org.slf4j.LoggerFactory;

/**
 * @author lingxue created on 6/28/16
 * @version v0.1
 **/

public class SuiteRunnerFactory {

    public static String JS_SUFFIX = ".js";
    public static String JAVA_PREFIX = "java#";
    public static String CUCUMBER_SUFFIX = ".feature";
    public static String JYTHON_PREFIX = "PY#";

    public static TestSuiteRunner getSuiteRunner(String suiteName){
        if(suiteName.endsWith(JS_SUFFIX)){
            return new JSTestSuiteRunner(suiteName);
        }
        else if(suiteName.startsWith(JAVA_PREFIX)){
            return new JavaTestSuiteRunner(suiteName);
        }
        else if(suiteName.endsWith(CUCUMBER_SUFFIX)){
            return new FeatureTestSuiteRunner(suiteName);
        }
        else if (suiteName.startsWith(JYTHON_PREFIX)){
            return new PythonSuiteRunner(suiteName);
        }
        else{
            LoggerFactory.getLogger(SuiteRunnerFactory.class).error("Can not find runner for suite: " + suiteName);
            return null;
        }
    }
}
