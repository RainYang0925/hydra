package com.xiaohongshu.automation.runner;

import com.xiaohongshu.automation.config.GenericConfig;
import com.xiaohongshu.automation.report.ReportGenerator;
import com.xiaohongshu.automation.result.LeafResult;
import cucumber.api.Scenario;
import cucumber.api.cli.Main;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.slf4j.LoggerFactory;

/**
 * @author lingxue created on 8/1/16
 * @version v0.1
 **/

public class FeatureTestSuiteRunner extends TestSuiteRunner {


    private LeafResult caseResult;

    public FeatureTestSuiteRunner(){}

    public FeatureTestSuiteRunner(String suiteName) {
        super(suiteName);
    }

    @Override
    public void runSuiteConcrete() {
        try {
            String suiteReportFolder = "detail/" + suiteName.trim().replace('/','_').
                    substring(0, suiteName.length() - SuiteRunnerFactory.CUCUMBER_SUFFIX.length());


            String[] paras = new String[]{
                    GenericConfig.INSTANCE.getCasehome() + suiteName,
                    "-g",
                    "com.mogujie.automation",
                    "-p",
                    "html:" + ReportGenerator.reportFolder + suiteReportFolder
            };
            Main.run(paras, Thread.currentThread().getContextClassLoader());
            TestSuiteRunner.suiteResult.setResultMsg("<a href='" + suiteReportFolder + "/index.html'>点击查看详细报告</a>");
        }
        catch (Exception e){
            LoggerFactory.getLogger(FeatureTestSuiteRunner.class).error("Execute Suite Fail: " + e.getMessage());
            e.printStackTrace();
            TestSuiteRunner.suiteResult.failWithMsg(e.getMessage());
        }
    }


    @Before
    public void beforeScenario(Scenario scenario){
        caseResult = new LeafResult(scenario.getName());
    }

    @After
    public void afterSenario(Scenario scenario){
        caseResult.testEnd();
        if(scenario.isFailed()){
            caseResult.fail();
        }
        TestSuiteRunner.suiteResult.addResult(caseResult);
    }

}
