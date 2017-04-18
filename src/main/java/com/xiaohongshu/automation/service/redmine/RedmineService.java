package com.xiaohongshu.automation.service.redmine;

import com.taskadapter.redmineapi.*;
import com.taskadapter.redmineapi.bean.Attachment;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.IssueFactory;
import com.taskadapter.redmineapi.bean.Project;
import com.xiaohongshu.automation.config.GenericConfig;
import org.apache.http.entity.ContentType;

import java.io.File;
import java.io.IOException;

/**
 * Created by zren on 17/3/21.
 */
public class RedmineService {
    private static final String STATUS_NEW = "新建" ;
    private String apiKey = GenericConfig.INSTANCE.getConfig("redmine.api.key");
    private String uri = GenericConfig.INSTANCE.getConfig("redmine.server.url");

    private RedmineManager redmine ;
    public RedmineManager getRedmineManager(){
        if (null==redmine) {
            redmine = RedmineManagerFactory.createWithApiKey(uri, apiKey);
        }
        return redmine ;
    }

    public Attachment uploadFile(File localFile) throws IOException, RedmineException {
        Attachment attachment = getRedmineManager().getAttachmentManager().uploadAttachment(ContentType.DEFAULT_BINARY.toString(), localFile);
        return attachment;
    }

    public Attachment addAttachmentToIssue(int issueId,File localFile) throws RedmineException, IOException {
        Attachment attachment = getRedmineManager().getAttachmentManager().addAttachmentToIssue(issueId, localFile, ContentType.DEFAULT_BINARY.toString());
        return attachment;
    }

    public Issue createIssue(String subject,String description,String projectKey,String assignee) throws RedmineException {
        Project prj = getProjectManager().getProjectByKey(projectKey);
        Issue issue  = IssueFactory.create(prj.getId(),subject);
        issue.setStatusName(STATUS_NEW);
        issue.setDescription(description);
        if (null!=assignee && !"".equals(assignee) ) {
            issue.setAssigneeName(assignee);
        }
        issue = getIssueManager().createIssue(issue);
        return issue ;
    }

    public IssueManager getIssueManager(){
        return getRedmineManager().getIssueManager();
    }

    public ProjectManager getProjectManager(){
        return getRedmineManager().getProjectManager();
    }
}
