package com.xiaohongshu.automation.service;

import com.xiaohongshu.automation.service.mwp.MWPTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author lingxue created on 6/21/16
 * @version v0.1
 **/

public class Test_MWPTest {

    MWPTest mwpTest;

    @Before
    public void prepareEnv(){
        mwpTest = new MWPTest();
    }

    @Test
    @Ignore
    public void testExecuteSuccess(){
//        mwpTest.setDataJson("{\n" +
//                "\t\"api\" : \"mwp.searchserviceonline.searchTips\",\n" +
//                "\t\"invalue\" : \"{\\\"keyword\\\": \\\"衣服\\\"}\"\n" +
//                "}");
//        Assert.assertTrue(mwpTest.execute());
//        Assert.assertEquals(mwpTest.getStateCode(), 200);
//        Assert.assertEquals(mwpTest.getRet(), "SUCCESS");
    }

    @Test
    @Ignore
    public void testExecuteFailWithInvalidPara(){
//        mwpTest.setDataJson("abc");
//        Assert.assertFalse(mwpTest.execute());
    }
}
