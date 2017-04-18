package com.xiaohongshu.automation.runner;

import com.xiaohongshu.automation.config.GenericConfig;
import com.xiaohongshu.automation.service.notify.NotifyTarget;
import com.xiaohongshu.automation.service.notify.WxNotifyTarget;
import com.xiaohongshu.automation.service.redmine.BugNotifyService;

import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * Created by zren on 17/3/27.
 */
public class OnlineBugNotifierRunner {
    NotifyTarget nft ;
    BugNotifyService service ;
    public OnlineBugNotifierRunner(){
        GenericConfig.INSTANCE.getConfig("redmine.notify.queryId");
        nft = new WxNotifyTarget();
        service = new BugNotifyService(37);
    }
    public void runTask(){

        service.doScan(nft);
    }

    public void schedule(){
        ScheduledThreadPoolExecutor e = new ScheduledThreadPoolExecutor(1);
        ScheduledFuture<?> scheduledFuture = e.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                runTask();
            }
        }, 1, 43200, TimeUnit.SECONDS);
        try {
            Object o = scheduledFuture.get();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }
    }
}
