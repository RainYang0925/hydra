package com.xiaohongshu.automation.runner;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @author lingxue created on 4/15/16
 * @version v0.1
 **/

public class JSRunner {

    private static final JSRunner JS_RUNNER = new JSRunner();
    private final ScriptEngine NASHORN_RUNNER = (new ScriptEngineManager()).getEngineByName("nashorn");

    //强化单例
    private JSRunner(){}

    public static JSRunner getRunner(){
        return JS_RUNNER;
    }

    public Object runJSCode(String code) throws ScriptException{
        Object result =  NASHORN_RUNNER.eval(code);
        return result;
    }

    public Object runJSCode(String code,Bindings in) throws ScriptException{
        Object result =  NASHORN_RUNNER.eval(code,in);
        return result;
    }

}
