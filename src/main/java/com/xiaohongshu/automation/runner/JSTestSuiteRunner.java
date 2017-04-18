package com.xiaohongshu.automation.runner;

import com.xiaohongshu.automation.config.PropertiesReader;
import com.xiaohongshu.automation.result.LeafResult;
import com.xiaohongshu.automation.utils.FileUtils;
import com.xiaohongshu.automation.utils.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.SimpleBindings;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lingxue created on 6/15/16
 * @version v0.1
 **/

public class JSTestSuiteRunner extends TestSuiteRunner{

    List<String> packages = new ArrayList<String>();
    public static boolean preLoadFlag = true;
    Logger logger = LoggerFactory.getLogger(JSTestSuiteRunner.class);

    public JSTestSuiteRunner(String suiteName) {
        super(suiteName);
        loadPackageList();

        if(preLoadFlag) {
            try {
                JSRunner.getRunner().runJSCode(preLoadEnv());  //预加载环境
            } catch (Exception e) {
                LoggerFactory.getLogger(JSTestSuiteRunner.class).error("预加载环境过程中出错: " + e.getMessage());
                e.printStackTrace();
            }
            preLoadFlag = false;
        }
    }

    @Override
    public void runSuiteConcrete() {
        try{
            SimpleBindings bindings = new SimpleBindings();
            LeafResult caseResult = null;
            bindings.put("PWD",new File(suitePath).getParent());
            bindings.put("suiteResult",TestSuiteRunner.suiteResult);
            bindings.put("caseResult",caseResult);
            bindings.put("logger",logger);
            JSRunner.getRunner().runJSCode(generateExecuteJS(),bindings);
        }
        catch (Exception e){
            TestSuiteRunner.suiteResult.failWithMsg(e.getMessage());
            LoggerFactory.getLogger(JSTestSuiteRunner.class).error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadPackageList() {
        PropertiesReader readerPackage = new PropertiesReader("js.package.properties");
        String[] propertiesValue = readerPackage.loadValues();
        for (int i = 0; i < readerPackage.sizeOfProperties(); i++) {
            packages.add(propertiesValue[i]);
        }
    }

    public String loadJSResoure(String resouce){
        return "load('" + ResourceUtils.encodeForJSPath(ResourceUtils.getResoucePath(resouce)) + "');\n";
    }

    public String loadJSFile(String path){
        String jsContent = "var jsfilePath ='" + ResourceUtils.encodeForJSPath(path) + "';\n";
        File jasmineBootStart = new File(ResourceUtils.getResoucePath("jasmine/boot_start.js"));
        try {
            jsContent += FileUtils.readStringFromFile(jasmineBootStart);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(JSTestSuiteRunner.class).error("Error when load js File: " + path);
        }
        return jsContent;
    }

    public String getImportPackage(){
        StringBuffer sb = new StringBuffer();
        sb.append("load('nashorn:mozilla_compat.js');");   //让nashorn可以使用importPackage语法
        String imPkg = "importPackage(";
        for (String pkg : packages) {
            sb.append(imPkg).append(pkg).append(");");
        }
        return sb.toString();
    }

    public String generateExecuteJS(){
        return preLoadEnv() + loadJSFile(suitePath);

    }

    public String preLoadEnv(){
        return loadJSResoure("jasmine/jasmine.js") + loadJSResoure("jasmine/jasmine-html.js") +
                loadJSResoure("npm/jvm-npm.js") + getImportPackage();
    }

}
