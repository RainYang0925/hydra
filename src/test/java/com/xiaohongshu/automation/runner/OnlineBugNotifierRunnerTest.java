package com.xiaohongshu.automation.runner;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zren on 17/3/27.
 */
public class OnlineBugNotifierRunnerTest {
    @Test
    public void testBugNotify(){
        OnlineBugNotifierRunner runner = new OnlineBugNotifierRunner();
        runner.schedule();
    }
}