package com.xiaohongshu.automation.service.redmine;

import com.taskadapter.redmineapi.Include;
import com.taskadapter.redmineapi.IssueManager;
import com.taskadapter.redmineapi.ProjectManager;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.IssueFactory;
import com.taskadapter.redmineapi.bean.Project;
import com.taskadapter.redmineapi.bean.Tracker;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by zren on 17/3/21.
 */
public class RedmineServiceTest {

    @Test
    public void testGetIssues(){
        RedmineService service = new RedmineService();
        IssueManager issueMgr = service.getRedmineManager().getIssueManager();
        Integer id = 2449;
        try {
            Issue issue = issueMgr.getIssueById(id, Include.journals);
        } catch (RedmineException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateIssue(){
        RedmineService service = new RedmineService();
        IssueManager issueMgr = service.getRedmineManager().getIssueManager();
        ProjectManager projectMgr = service.getRedmineManager().getProjectManager();
        try {
            Project project = projectMgr.getProjectByKey("quality-test-infrastrucutre");
            Issue issue = IssueFactory.create(project.getId(),"Test created by Rest API...");
            issue.setAuthorName("zren");
            issue.setDescription("由Rest API 测试创建。");
            issue.setStatusName("新建");
            issueMgr.createIssue(issue);
        } catch (RedmineException e) {
            e.printStackTrace();
        }


    }


    @Test
    public void testAttachement(){
        RedmineService service = new RedmineService();
        try {
            service.addAttachmentToIssue(7701,new File("/Users/zren/projects/Hydra/js/feature/test.control"));
        } catch (RedmineException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}