package com.xiaohongshu.automation.runner;

import com.xiaohongshu.automation.config.GenericConfig;
import com.xiaohongshu.automation.report.ReportGenerator;
import com.xiaohongshu.automation.result.CompositeResult;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author lingxue created on 6/22/16
 * @version v0.1
 **/

public class TestRunner {

    public static void runFromControlFile(String controlFile){
        LoggerFactory.getLogger(TestRunner.class).info("-----------------Running From Control File: " + controlFile + " --------------------");
        GenericConfig config = GenericConfig.INSTANCE;
        CompositeResult result = new CompositeResult(controlFile);
        try {
            String controlFilePath = config.getCasehome() + controlFile;
            LoggerFactory.getLogger(TestRunner.class).info("Control File path: " + controlFilePath);
            BufferedReader suitesFileReader = new BufferedReader(new FileReader(controlFilePath));
            String suiteName;
            while ((suiteName = suitesFileReader.readLine()) != null ){
                if(TestSuiteRunner.suiteShouldBeRun(suiteName)) {
                    TestSuiteRunner testSuiteRunner = SuiteRunnerFactory.getSuiteRunner(suiteName);
                    if (testSuiteRunner != null) {
                        result.addResult(testSuiteRunner.runSuite());
                    }
                    else{
                        CompositeResult suiteResult = new CompositeResult(suiteName);
                        suiteResult.failWithMsg("Do not execute due to no runner!");
                        suiteResult.testEnd();
                        result.addResult(suiteResult);
                    }
                }
            }
        }
        catch (FileNotFoundException e){
            LoggerFactory.getLogger(TestRunner.class).error("Control File not exist: " + controlFile);
            result.failWithMsg("Control File not exist: " + controlFile);
            e.printStackTrace();
        }
        catch (IOException e){
            LoggerFactory.getLogger(TestRunner.class).error("IO exception where read controlFile: " + controlFile);
            result.fail();
            e.printStackTrace();
        }
        result.testEnd();
        ReportGenerator.generaReportFromResult(result);
        LoggerFactory.getLogger(ReportGenerator.class).info("------------- Finish! ----------------");
        System.exit(result.isPass()? 0:1);
    }

    public static void runFromSuite(String suiteName){
        CompositeResult result = new CompositeResult(suiteName);
        TestSuiteRunner testSuiteRunner = SuiteRunnerFactory.getSuiteRunner(suiteName);
        if(testSuiteRunner != null){
            result.addResult(testSuiteRunner.runSuite());
        }
        result.testEnd();
        ReportGenerator.generaReportFromResult(result);
        System.exit(result.isPass()? 0:1);
    }

    public static void main(String[] args) throws Exception{
         runFromControlFile("feature/test.control");
      //  runFromControlFile("Demo/http.control");
       // runFromSuite("java#com.mogujie.automation.testng.SimpleTest");
    }
}
