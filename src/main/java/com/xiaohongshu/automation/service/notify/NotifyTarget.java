package com.xiaohongshu.automation.service.notify;

/**
 * Created by zren on 17/3/27.
 */
public interface NotifyTarget {
    public boolean notifyTo(String to,String msg);
}
