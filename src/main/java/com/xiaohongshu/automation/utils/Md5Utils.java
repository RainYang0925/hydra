package com.xiaohongshu.automation.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Created by yuanfei on 16/7/6.
 */
public class Md5Utils {
    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD532(String plainText){
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
             result = buf.toString();  //md5 32bit


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }


    // 测试主函数
    public static void main(String args[]) {
        String s = new String("wangyuanfei");
        System.out.println(string2MD532(s));

        System.out.println(string2MD516(s));


    }


    public static String string2MD516(String plainText) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString().substring(8, 24);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

}
