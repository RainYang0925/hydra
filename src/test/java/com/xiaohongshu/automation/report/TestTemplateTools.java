package com.xiaohongshu.automation.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaohongshu.automation.utils.FileUtils;
import com.xiaohongshu.automation.utils.TemplateTool;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by zren on 17/3/15.
 */
public class TestTemplateTools {


    @Test
    public void testRenderTemplate(){
        String tempFile = "/tmp/a.vm";
        String dataFile = "/tmp/a.json";
        String outFile = "/tmp/a.out";
        Context context = buildContext(dataFile);
        try {
            StringBuilder outBuilder = new StringBuilder();
            Velocity.evaluate(context,new StringBuilderWriter(outBuilder),"test",new FileReader(tempFile));
            String out = outBuilder.toString();
            System.out.println(out);
            FileUtils.writeStringToFile(out,new File(outFile),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testTemplateTool(){
        String outFile = "/tmp/a.out";

        TemplateTool.main(new String[]{});//should print usage

        TemplateTool.main(new String[]{"/tmp/a.vm"});

        Assert.assertTrue(new File(outFile).exists());

        FileUtils.deleteFile(outFile);


        TemplateTool.main(new String[]{"/tmp/a.vm","/tmp/a.json"});
        Assert.assertTrue(new File(outFile).exists());

        FileUtils.deleteFile(outFile);

        String newOutFile = "/tmp/b.out";


        TemplateTool.main(new String[]{"/tmp/a.vm","/tmp/a.json",newOutFile});

        Assert.assertTrue(new File(newOutFile).exists());
        //FileUtils.deleteFile(newOutFile);
    }


    @Test
    public void testFileUtils(){
        String name = FileUtils.getFileNameWithoutSuffix(new File("/tmp/a.vm"));
        Assert.assertTrue(name.equals("a"));

        Assert.assertTrue(new File("/tmp/a.vm").getAbsolutePath().equals("/tmp/a.vm"));

        Assert.assertTrue(new File("/tmp/a.vm").getParentFile().getAbsolutePath().equals("/tmp"));

        Assert.assertTrue(!new File("/tmp/a.vm").getParentFile().getAbsolutePath().equals("/tmp/"));

    }

    private Context buildContext(String dataFile) {
        try {
            String json = FileUtils.readStringFromFile(new File(dataFile));
            JSONObject prop = JSON.parseObject(json);
            VelocityContext ret = new VelocityContext(prop);
            return ret ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
