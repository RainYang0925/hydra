package com.xiaohongshu.automation.report;

import com.xiaohongshu.automation.config.PropertiesReader;
import com.xiaohongshu.automation.result.CompositeResult;
import com.xiaohongshu.automation.utils.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.io.File;
import java.util.Date;
import java.util.Properties;

/**
 * @author lingxue created on 6/23/16
 * @version v0.1
 **/

public class ReportGenerator {

    public static String reportFolder = "report/";
    public static String reportTemplate = reportFolder + "reportTemplate.vm";
    public static String reportPath = reportFolder + "result.xml.html";  //历史原因保留这个名字
    public static String reportBackupPath = reportFolder + "backup" + "/" + "report_";
    static Logger logger = LoggerFactory.getLogger(ReportGenerator.class);

    public static void generaReportFromResult(CompositeResult result){
        logger.info("------------- Generate Test Report ----------------");
        Properties prop = new PropertiesReader("velocity.properties").getProp();
        prop.list(System.out);
        Velocity.init(prop);
        VelocityContext context = new VelocityContext();
        context.put("result", result);

        Template template = Velocity.getTemplate(reportTemplate);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        File report = new File(reportPath);
        File backupReport = new File(reportBackupPath + new Date().getTime() + ".html");
        try {
            FileUtils.writeStringToFile(writer.toString(), report, false);
            FileUtils.copyFile(report, backupReport);
        }
        catch (Exception e){
            LoggerFactory.getLogger(ReportGenerator.class).error("Generate Report Fail: " + e.getMessage());
            e.printStackTrace();
        }
        LoggerFactory.getLogger(ReportGenerator.class).info("Generate Report Success: " + reportPath);
    }
}
