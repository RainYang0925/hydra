package com.xiaohongshu.automation.runner;

import com.xiaohongshu.automation.config.GenericConfig;
import com.xiaohongshu.automation.result.LeafResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.SimpleBindings;

/**
 * Created by zren on 17/3/7.
 */
public class PythonSuiteRunner extends TestSuiteRunner {

    Logger logger = LoggerFactory.getLogger(PythonRunner.class);
    public PythonSuiteRunner(String suiteName){
        super(suiteName);
        this.suitePath = GenericConfig.USER_HOME+"/python/"+ locateFile();


    }
    @Override
    public void runSuiteConcrete() {
        SimpleBindings bindings = new SimpleBindings();
        LeafResult caseResult = null;

        String fileName = locateFile();
        logger.info("Will execute the Python Script:"+fileName);
        PythonRunner.getInstance().runPythonFile(fileName);

    }

    /**
     * 根据SuiteName来查找对应的PythonFile
     * @return
     */
    private String locateFile() {
        return suiteName.replace("PY#","");
    }
}
