package com.xiaohongshu.automation.utils;

import java.util.Date;

/**
 * @author lingxue created on 6/24/16
 * @version v0.1
 **/

public class DateUtils {

    private Date startTime = new Date();
    private Date endTime;

    public String getStartTime(){
        return startTime.toString();
    }

    public String getEndTime(){
        return endTime.toString();
    }

    public void setEndTime(){
        endTime = new Date();
    }

    public double getTimeCost(){
        return (double)(endTime.getTime() - startTime.getTime())/1000;
    }
}
