package com.xiaohongshu.automation.service.notify;

import com.xiaohongshu.automation.service.webdriver.pages.WXPageObject;

/**
 * Created by zren on 17/3/27.
 */
public class WxNotifyTarget implements NotifyTarget {

    WXPageObject wxClient ;

    public WxNotifyTarget(){
        wxClient = new WXPageObject();
    }
    @Override
    public boolean notifyTo(String to, String msg) {
        boolean b = wxClient.sendMsgTo(msg, to);
        return b;
    }
}
