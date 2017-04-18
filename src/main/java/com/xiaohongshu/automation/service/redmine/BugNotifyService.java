package com.xiaohongshu.automation.service.redmine;

import com.taskadapter.redmineapi.Include;
import com.taskadapter.redmineapi.Params;
import com.taskadapter.redmineapi.ProjectManager;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.Project;
import com.taskadapter.redmineapi.bean.SavedQuery;
import com.xiaohongshu.automation.config.GenericConfig;
import com.xiaohongshu.automation.service.notify.NotifyTarget;

import java.util.Date;
import java.util.List;

/**
 * Created by zren on 17/3/27.
 */
public class BugNotifyService {

    private Integer queryId = 34 ;
    private String toUser = GenericConfig.INSTANCE.getConfig("redmine.notify.to");
    public BugNotifyService(int queryId){
        this.queryId = queryId ;
    }



    /**
     * 获得该项目下优先级为紧急或者立刻的，但是Assignee为空的。来通知出来。
     * @param to
     */
    public void doScan(NotifyTarget to){
        RedmineService service = new RedmineService();

        try {
            List<SavedQuery> queries = service.getIssueManager().getSavedQueries();
            for (SavedQuery query: queries
                 ) {
                if (query.getId()== queryId){
                    List<Issue> issues = service.getIssueManager().getIssues("", query.getId(), Include.journals);
                    doNotify(issues,to);
                }
            }

        } catch (RedmineException e) {
            e.printStackTrace();
        }
    }

    private void doNotify(List<Issue> issues, NotifyTarget to) {
        for (Issue issue: issues
             ) {
            String msg = buildMsg(issue);

            to.notifyTo(toUser,msg);
        }
    }

    private String buildMsg(Issue issue) {
        Date createdOn = issue.getCreatedOn();
        String link = "redmine.devops.xiaohongshu.com/issues/" + issue.getId();
        return new StringBuffer().append(issue.toString()).append("\n").append("创建时间为："+createdOn.toString()).append("\n").append(link).toString();
    }
}
