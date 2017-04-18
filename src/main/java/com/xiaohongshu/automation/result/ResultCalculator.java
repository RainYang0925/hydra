package com.xiaohongshu.automation.result;

import java.text.NumberFormat;

/**
 * @author lingxue created on 6/23/16
 * @version v0.1
 **/

public class ResultCalculator {

    public static String getPercentageRate(int num, int sum){
        if(sum == 0){
            return "0.00%";
        }
        else{
            double percentage = (float)num/sum;
            NumberFormat nf = NumberFormat.getPercentInstance();
            nf.setMinimumFractionDigits(2);
            return nf.format(percentage);
        }
    }
}
