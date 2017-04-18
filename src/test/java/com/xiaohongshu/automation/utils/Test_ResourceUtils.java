package com.xiaohongshu.automation.utils;

import org.junit.Test;
import org.junit.Assert;

/**
 * @author lingxue created on 6/22/16
 * @version v0.1
 **/

public class Test_ResourceUtils {

    @Test
    public void testGetResourcePathNotExist(){
        Assert.assertEquals("", ResourceUtils.getResoucePath("jasmine/l.js"));
    }

    @Test
    public void testGetResourcePathExist(){
        Assert.assertTrue(ResourceUtils.getResoucePath("jasmine/jasmine.js").contains("jasmine.js"));
    }
}
