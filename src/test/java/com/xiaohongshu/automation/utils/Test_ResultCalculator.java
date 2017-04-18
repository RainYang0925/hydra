package com.xiaohongshu.automation.utils;

import com.xiaohongshu.automation.result.ResultCalculator;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author lingxue created on 6/23/16
 * @version v0.1
 **/

public class Test_ResultCalculator {

    @Test
    public void testGetPercentageRateBeZero(){
        Assert.assertEquals(ResultCalculator.getPercentageRate(0, 0),"0.00%");
    }

    @Test
    public void testGetPercentageRage(){
        Assert.assertEquals(ResultCalculator.getPercentageRate(1,2),"50.00%");
    }
}
