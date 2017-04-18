package com.xiaohongshu.automation.runner;

import com.xiaohongshu.automation.config.GenericConfig;
import com.xiaohongshu.automation.utils.FileUtils;
import com.xiaohongshu.automation.utils.PropertiesReader;
import org.apache.tools.ant.taskdefs.Classloader;
import org.python.core.JythonInitializer;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.core.adapter.ExtensiblePyObjectAdapter;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.SimpleBindings;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by zren on 17/3/7.
 */
public class PythonRunner {
    public static final String LR = "\n";
    Logger logger = LoggerFactory.getLogger(PythonRunner.class);

    private static final String PYTHON_PROPERTIES = "python.properties";
    private static PythonRunner inst = new PythonRunner();

    private PythonRunner(){
        //initializer: //setup the env ;

        PropertiesReader.initialize(PYTHON_PROPERTIES);

    }

    public static PythonRunner getInstance() {return inst;}


    public Object runPythonCode(String code, SimpleBindings bindings){
        System.setProperty("python.import.site","false");
        PythonInterpreter py = new PythonInterpreter();
        py.initialize(System.getProperties(),System.getProperties(),new String[]{""});

        StringBuffer realCode = new StringBuffer(genLoad3rdPackages()).append(code);
        py.exec(realCode.toString());
        return null ;
    }

    public Object runPythonFile(String pyFile){
        Object ret = null ;
        String s = null;

        File realFile = locateFile(pyFile);
        try {
            s = FileUtils.readStringFromFile(realFile);
            ret = runPythonCode(s,null);
        } catch (IOException e) {
            logger.error("Fail to execute Python file:"+pyFile,e);
            e.printStackTrace();
        }
        return ret ;
    }

    /**
     * 利用http://docs.pytest.org/en/latest/usage.html#calling-pytest-from-python-code
     * pytest.main(['-x', 'mytestdir'])
     * @param testDir
     * @return
     */
    public Object runPyTest(String testDir){
        StringBuffer runCode = new StringBuffer(genLoad3rdPackages());
        runCode.append("import pytest").append(LR);
        runCode.append("pytest.main(['-x','").append(testDir).append("'])").append(LR);
        System.setProperty("python.import.site","false");
        PythonInterpreter py = new PythonInterpreter();
        py.initialize(System.getProperties(),System.getProperties(),new String[]{""});
        py.exec(runCode.toString());
        return null;
    }

    private File locateFile(String pyFile) {
        String fileName = GenericConfig.USER_HOME + "/python/" +pyFile;
        File ret = new File(fileName);
        if (!ret.exists()){
            URL resource = getClass().getClassLoader().getResource(pyFile);
            if (null!=resource){
                String path = resource.getPath();
                String name = resource.getFile() ;
                ret = new File(path);
            }
        }
        return ret;
    }

    private String genLoad3rdPackages(){
        StringBuffer sb = new StringBuffer("import sys").append(LR);
        String packagePath = PropertiesReader.loadValue("python.module.path");
        File dir = new File(packagePath);
        if (dir.exists()){
            File[] packages = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.isDirectory()) return true ;
                    return false;
                }
            });
            sb.append("sys.path.append(\"").append(packagePath).append("\")").append(LR);
            if (null!=packages){
                for (int i=0;i<packages.length;i++) {
                    File pck = packages[i];
                    sb.append("sys.path.append(\"").append(pck.getAbsolutePath()).append("\")").append(LR);
                }
            }

        }
        return sb.toString();

    }
}
