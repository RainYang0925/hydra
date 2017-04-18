package com.xiaohongshu.automation.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zren on 17/3/15.
 */
public class TemplateTool {
    public static String evaluate(String temp, Map data){
        VelocityContext context = new VelocityContext(data);
        StringBuilder sb = new StringBuilder();
        Velocity.evaluate(context,new StringBuilderWriter(sb),"test",new StringReader(temp));
        return sb.toString();
    }

    public static boolean evaluate(File tempFile,File dataFile,File outFile)  {
        try {
            String temp = FileUtils.readStringFromFile(tempFile);
            Map data = null;
            if (dataFile.getName().endsWith(".json")) {
                data = JSON.parseObject(FileUtils.readStringFromFile(dataFile));
            } else {
                Properties prop = new Properties();
                prop.load(new FileReader(dataFile));
                data = prop;
            }
            String outStr = evaluate(temp, data);
            FileUtils.writeStringToFile(outStr, outFile, false);
            return true;
        }catch ( IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public static void printUsage(){
        System.out.println("Usage:");
        System.out.println("    java -classpath hydra.jar com.xiaohongshu.automation.utils.TemplateTool tempFile [dataFile] [outFile]");
    }

    public static  void main(String[] args){
        if (args.length<1){
            printUsage();
            System.exit(1);
        }
        String tempFileName = "";
        String dataFileName = "";
        String outFileName = "";
        switch ( args.length){
            case 1:
                tempFileName = args[0];
                evaluate(tempFileName);
                System.exit(0);
                break;
            case 2:
                tempFileName = args[0];
                File tempFile = new File(tempFileName) ;
                dataFileName = args[1];
                String name = FileUtils.getFileNameWithoutSuffix(tempFile);
                String path = tempFile.getParentFile().getPath();
                evaluate(tempFile,new File(dataFileName),new File(path+"/"+name+".out"));
                System.exit(0);
                break;
            case 3:
                tempFileName = args[0];
                tempFile = new File(tempFileName) ;
                dataFileName = args[1];
                outFileName = args[2];
                evaluate(tempFile,new File(dataFileName),new File(outFileName));
                System.exit(0);
                break;
            default:
                printUsage();
                System.exit(1);

        }

    }

    private static boolean evaluate(String tempFileName) {
        File tempFile = new File(tempFileName);
        //看看是否有同名的dataFile。
        String name = FileUtils.getFileNameWithoutSuffix(tempFile);
        File[] dataFiles = tempFile.getParentFile().listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().equals(tempFile.getName()))
                    return false;
                if (pathname.getName().startsWith(name)) return true;
                return false;
            }
        });
        if (dataFiles.length>0){
            boolean evaluate = evaluate(tempFile, dataFiles[0], new File(tempFile.getParentFile().getAbsolutePath() + "/" + name + ".out"));
            if (evaluate){
                return true ;
            }
        }
        return false ;

    }


}
