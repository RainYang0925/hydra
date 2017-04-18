package com.xiaohongshu.automation.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author lingxue created on 6/22/16
 * @version v0.1
 **/

public class Test_GenericConfig {

    private GenericConfig config;

    @Before
    public void prepareEnv(){
        config = GenericConfig.INSTANCE;
    }

    @Test
    public void testCaseHome(){
        Assert.assertTrue(config.getCasehome().contains("js"));
    }
}
