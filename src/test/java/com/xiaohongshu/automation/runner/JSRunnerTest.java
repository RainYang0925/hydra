package com.xiaohongshu.automation.runner;

import com.xiaohongshu.automation.report.ReportGenerator;
import com.xiaohongshu.automation.result.CompositeResult;
import org.junit.Test;

/**
 * Created by zren on 17/3/8.
 */
public class JSRunnerTest {


    @Test
    public void testExecuteJS(){
        TestRunner.runFromControlFile("feature/test.control");

    }

    @Test
    public void runJSCode(){
        JSTestSuiteRunner runner = new JSTestSuiteRunner("arthur/test.js");
        runner.runSuite();
    }
}
