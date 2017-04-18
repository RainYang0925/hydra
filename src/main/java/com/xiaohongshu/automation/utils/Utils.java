package com.xiaohongshu.automation.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Created by yuanfei on 16/4/28.
 */
public class Utils {

    public static String replace;

    public  static String getReplace() {
        return replace;
    }

    public  static void setReplace(String replace) {
        Utils.replace = replace;
    }


    public static void waitTime(int time){
        try {
            LoggerFactory.getLogger(Utils.class).info("------Begin to Wait Time: " + time + " milliseconds------");
            Thread.sleep(time);
            LoggerFactory.getLogger(Utils.class).info("------Wait Time Finish------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String replace(String formData) {
        if(Utils.replace == null || Utils.replace.equals("")){
            return formData;
        }
        else if(formData == null || formData.equals("")){
            return formData;
        }
        else {
            try {
                JSONObject reJson = JSON.parseObject(Utils.replace);
                JSONObject formJson = JSON.parseObject(formData);
                Iterator reIt = reJson.keySet().iterator();
                while (reIt.hasNext()) {
                    String currentKey = reIt.next().toString();
                    if (!formJson.containsKey(currentKey)) {
                        formJson.put(currentKey, reJson.get(currentKey));
                    }
                }
                formData = formJson.toString();
            }
            catch (Exception e){
                LoggerFactory.getLogger(Utils.class).error("替换参数过程中出错: " + e.getMessage());
                e.printStackTrace();
            }
            return formData;
        }
    }

    public static void clearReplace(){
        setReplace("");

    }

    public static void setEnv(String host,String ...port){

        System.getProperties().setProperty("http.proxyHost", host);
        if(port.length==0){

            port = new String[]{"3128"};

        }
        String ports = port[0];
        System.out.println(ports);
        System.getProperties().setProperty("http.proxyPort", ports);

    }

    public static String idToUrl(long id, int version) {
        StringBuilder sb = new StringBuilder();
        if(id > 0L) {
            sb.append(version);
            sb.append((new BigInteger(String.valueOf(id * 2L + 56L), 10)).toString(36));
        }

        return sb.toString();
    }

    public static String idToUrl(long id) {
        return idToUrl(id, 1);
    }

    public static Long urlToId(String url) {
        Long reInt = null;
        if(url != null && !url.isEmpty() && url.length() > 1) {
            url = url.substring(1, url.length());
            reInt = Long.valueOf(Long.parseLong((new BigInteger(url, 36)).toString(10)));
            reInt = Long.valueOf((reInt.longValue() - 56L) / 2L);
            reInt = Long.valueOf(reInt.longValue() > 0L?reInt.longValue():0L);
        }

        return reInt;
    }


    public static String getSchema(String string){
        String schema="";
        String json ="";
        if(string.endsWith(".json")){
            try {
                json = FileUtils.readStringFromFile(new File(string));
            }catch (IOException e){
                LoggerFactory.getLogger(Utils.class).error("read string from file error: " + e.getMessage());
            }
        }
        else{
            json = string;
        }
        JSONObject schemaObj = JsonSchemaUtils.jsonString2ObjectSchema(json);
        schema = JsonSchemaUtils.formatJson(schemaObj.toString());
        LoggerFactory.getLogger(Utils.class).info("get schema string successfully");
        return schema;
    }

    public static void writeSchemaToFile(String schema,String filePath){
        try {
            FileUtils.writeStringToFile(schema, new File(filePath), false);

        }catch (IOException e) {
            LoggerFactory.getLogger(Utils.class).error("write schema string to file error: " + e.getMessage());
        }
        LoggerFactory.getLogger(Utils.class).info("write schema string to file successfully");
    }

    public static JSONObject matchSchema(String response,String string){
        String schema = "";
        JSONObject jsonObject;
        if(string.endsWith(".json")){
            try{
                schema = JsonSchemaUtils.getJsonFile(string);
            }catch (Exception e){
                LoggerFactory.getLogger(Utils.class).error("read schema from file error: " + e.getMessage());
            }
        }else{
            schema = string;
        }
        jsonObject = JsonSchemaUtils.matchJsonStringWithSchema(response,schema);
        return jsonObject;
    }

    public static Boolean matchActualWithSchema(String response,String string){
        Boolean b = false;
        JSONObject result;
        try{
            result = Utils.matchSchema(response,string);
            if(result.get("schemaResult").equals("success")){
                b=true;
                LoggerFactory.getLogger(Utils.class).info("schema match successfully");
            }
            else {
                LoggerFactory.getLogger(Utils.class).info(result.toJSONString());
            }
        }catch(Exception e){
            LoggerFactory.getLogger(Utils.class).error("schema match error" + e.getMessage());
        }
        return  b;
    }

    /**
     * 通过class文件,来获取文件目录的绝对路径
     * @param obj
     * @return
     */
    public static String getClassFolderPath(Class obj){

        String folder = obj.getResource("").getPath();
        //判断是通过Entry调用还是通过runTest.sh去调用
        if(folder.contains("!")){
            int splitEndPoint = folder.indexOf("!");
            int splitStartPoint = folder.indexOf("/lib/target");
            String replaceString = folder.substring(splitStartPoint,splitEndPoint + 1);
            folder = folder.replace(replaceString , "/src/main/java");
            if(folder.contains("file:")){
                String header = "file:";
                folder = folder.substring(header.length());
            }
        }else{
            folder = folder.replace("/target/classes/", "/src/main/java/");
        }
        return  folder;
    }

    public static String getLocalIP(){
        String ip = "unKnown";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address instanceof Inet4Address) {
                        ip = address.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            //Lurker.selfLog(e.getMessage());
        } catch (Exception e) {
            //Lurker.selfLog(e.getMessage());
        }

        return ip;
    }

    public static void main(String []args){

//        String schema = Utils.getSchema("/Users/yuanfei/evaGit/InterfaceTest/js/Demo/tool/schemaMatch/AlipaySchema.json");
//        System.out.println(schema);
//        Utils.writeSchemaToFile(schema, "/Users/yuanfei/evaGit/InterfaceTest/js/Demo/eva.json");
//        System.out.println((Utils.matchActualWithSchema("{\"status\":{\"code\":1002,\"msg\":\"\\u8bf7\\u8f93\\u5165\\u7528\\u6237\\u540d\\uff01\"},\"result\":123}", schema)));
        //Utils.setReplace("{\"a\":4j5}");


    }



}




