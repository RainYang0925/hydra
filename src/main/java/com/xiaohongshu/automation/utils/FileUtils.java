package com.xiaohongshu.automation.utils;

import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author lingxue created on 6/21/16
 * @version v0.1
 **/

public class FileUtils {

    public static final String LINE_SEPERATOR = System.getProperty("line.separator");
    public static final String[] LINE_EXCLUDE = {"//","#"};

    public static String readStringFromFile(File file) throws IOException {
        try {
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
                buffer.append(LINE_SEPERATOR);
            }
            reader.close();
            return (buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("problem with reading the following file: " + file.getPath() + File.separator
                    + file.getName());
        }
    }

    /**
     * 说 明:把字符串写入文件中
     * @param data: String 待写入数据
     * @param file: File 待写入的目标文件(对象)
     * @param append: boolean 是否是在文件末尾追加
     * */
    public static void writeStringToFile(String data, File file, boolean append) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
        writer.write(data);
        writer.close();
    }

    public static boolean copyFile(File fromFile, File toFile) {
        boolean rtn = false;
        try {
            org.apache.commons.io.FileUtils.copyFile(fromFile, toFile);
            rtn = true;
        } catch (Exception e) {
            LoggerFactory.getLogger(FileUtils.class).info(e.getMessage());
            rtn = false;
        }
        return rtn;
    }

    /**
     *说明: 删除文件
     *@param : path待删除文件绝对
     */
    public static void deleteFile(String path){
        if ("" != path) {
            File file = new File(path);
            if (file.exists())
                file.delete();
        }
    }

    public static String getFileNameWithoutSuffix(File file){
        String name = file.getName();
        if (name.indexOf(".")>0){
            int i = name.lastIndexOf(".");
            return name.substring(0,i);
        }
        return name ;
    }
}
