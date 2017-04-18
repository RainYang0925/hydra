package com.xiaohongshu.automation.service.tesla;

import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.mogujie.qa.intertest.support.Reflection;
//import com.mogujie.qa.intertest.support.Type;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lingxue created on 6/30/16
 * @version v0.1
 **/

public class TeslaParaHelper {
//
//    public static String getAllInfoFromService(String service){
//        try {
//            Class serviceClazz = Reflection.getClass(service);
//            Method[] methods = serviceClazz.getMethods();
//            List<String> methodNames = new ArrayList<String>();
//            for (Method med : methods) {
//                methodNames.add(med.getName());
//            }
//            return methodNames.toString();
//        } catch (Exception ex) {
//            return ex.toString();
//        }
//    }
//
//    public static String getInclassFromServiceAndMethod(String service,String methodStr){
//        try {
//            Class serviceClazz = Reflection.getClass(service);
//            Method method = Reflection.getMethod(serviceClazz, methodStr);
//            String parameters = "";
//            if (method.getParameters().length < 1) {
//                return "method has not parameters";
//            } else if (method.getParameters().length == 1) {
//                return method.getParameters()[0].getParameterizedType().getTypeName();
//            }
//            for (Parameter param : method.getParameters()) {
//                parameters += param.getParameterizedType().getTypeName() + " , ";
//            }
//            return parameters.substring(0, parameters.length() - 3);
//        } catch (Exception ex) {
//            return ex.toString();
//        }
//    }
//
//    public static String getInValueFromServiceAndMethod(String service,String methodStr){
//        try {
//            String inclass = getInclassFromServiceAndMethod(service, methodStr);
//            String[] inclassArr = inclass.split(" , ");
//            String inValues = "";
//            for (String inClass : inclassArr) {
//                Class inClazz = Reflection.getClass(inClass);
//
//                if (Type.isPrimative(inClazz)) {
//                    inValues += Type.getPrimativeValue(inClazz) + " ; ";
//                }
//                else if(inClazz == Double.class || inClazz == Float.class){
//                    inValues += "1.1" + " ; ";
//                }
//                else if(inClazz == Short.class){
//                    inValues += "1" + " ; ";
//                }
//                else if(inClazz == Boolean.class){
//                    inValues += Boolean.TRUE + " ; ";
//                }
//                else if(inClazz.isEnum()){
//                    inValues += "\"" + inClazz.getEnumConstants()[0] + "\"";
//                }
//                else {
//                    String objStr = com.alibaba.fastjson.JSON.toJSONString(getObjectFromClass(inClazz), SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullListAsEmpty);
//                    inValues += objStr + " ; ";
//                }
//            }
//
//            if (inValues.endsWith(" ; ")) {
//                inValues = inValues.substring(0, inValues.length() - 3);
//            }
//
//            return inValues;
//
//        } catch (Exception ex) {
//            return ex.toString();
//        }
//    }
//
//    public static Object getObjectFromClass(Class<?> clazz){
//        try {
//            Constructor<?> con = clazz.getConstructor();
//            Object obj = con.newInstance();
//            Field[] fields = clazz.getDeclaredFields();
//
//            for (Field field : fields) {
//                if(field.getType() == Boolean.class){
//                    Method method = clazz.getMethod("set" + firstCharToBig(field.getName()),field.getType());
//                    method.invoke(obj,Boolean.TRUE);
//                }
//                else if(field.getType().isEnum()){
//                    Method method = clazz.getMethod("set" + firstCharToBig(field.getName()),field.getType());
//                    method.invoke(obj,field.getType().getEnumConstants()[0]);
//                }
//                else if(!(Type.isPrimative(field.getType()))){
//                    Method method = clazz.getMethod("set" + firstCharToBig(field.getName()),field.getType());
//                    method.invoke(obj, getObjectFromClass(field.getType()));
//                }
//                else if(field.getType().isAssignableFrom(List.class)){
//                    java.lang.reflect.Type fc = field.getGenericType();
//                    if(fc == null) continue;
//                    if(fc instanceof ParameterizedType){
//                        ParameterizedType pt = (ParameterizedType) fc;
//                        Class genericClazz = (Class)pt.getActualTypeArguments()[0];
//                        Method method = clazz.getMethod("set" + firstCharToBig(field.getName()),field.getType());
//                        List list = (List)Class.forName("java.util.ArrayList").newInstance();
//                        list.add(getObjectFromClass(genericClazz));
//                        method.invoke(obj, list);
//                    }
//                }
//                else if (Type.isPrimative(field.getType()) || field.getType() == Double.class || field.getType() == Float.class || field.getType() == Short.class) {
//
//                }
//            }
//            return obj;
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static String firstCharToBig(String str){
        char[] cs=str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
        //return str.replace(str.substring(0,1),str.substring(0,1).toUpperCase());
    }

    public static void main(String[] args) {
        String service = "com.mogujie.pay.mailo.api.MaiLoInstallmentApi";
////        System.out.println(getAllInfoFromService(service));
        String methodStr = "checkIsCanInstallment";
//        System.out.println(getInclassFromServiceAndMethod(service, methodStr));
//        System.out.println(getInValueFromServiceAndMethod(service, methodStr));
//    //  System.out.println( firstCharToBig("isMosaiiismoc"));
//
//        System.out.println();
//        System.out.println(Enum.class);

    //    System.out.println(com.alibaba.fastjson.JSON.toJSONString(getObjectFromClass(Test.class),SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullListAsEmpty));

    }

}

