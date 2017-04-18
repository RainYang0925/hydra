package com.xiaohongshu.automation.runner;

import org.testng.annotations.Test;

/**
 * Created by zren on 17/3/7.
 */
public class PythonRunnerTest {
    @Test
    public void testRunPythonCode() throws Exception {
        String code = "import sys \nsys.executable='' \nprint sys.executable";
        PythonRunner.getInstance().runPythonCode(code,null);
    }


    @Test
    public void testRunPythonFile() {

        String file = "pyex.py";
        PythonRunner.getInstance().runPythonFile(file);
    }


    @Test
    public void testPytest(){
        PythonRunner.getInstance().runPyTest("/Users/zren/projects/omega/tests/adam");
    }

}