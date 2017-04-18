package com.xiaohongshu.automation.utils;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import java.io.IOException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;



/**
 * Created by yuanfei on 16/6/23.
 */
public class JsonSchemaUtils {

    public static String schemaResult = "success";
    public static String  properties ="JsonSchemaProperties";

    /**
     * 实现从json文件里读取json字符串
     * @param filePath 文件名
     * @return json字符串
     */
    public static String getJsonFile(String filePath)
    {
        File file = new File(filePath);
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try{
            if(file.isFile() && file.exists())
            {
                scanner = new Scanner(file,"utf-8");
                while (scanner.hasNextLine())
                {
                    buffer.append(scanner.nextLine());
                }
            }
            else {

                LoggerFactory.getLogger(JsonSchemaUtils.class).
                        error("file  not exit");
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();

        }finally {
            if(scanner != null)
            {
                scanner.close();
            }
        }
        return buffer.toString();
    }


    /**
     * 判断一个JSONObject是否在ArrayList<JSONObject>中
     *@param elemProperties:JSONObject
     *@return Boolean
     */
    public static Boolean isSchemaObjInArray(JSONObject elemProperties,JSONArray arrayList) throws JSONException{
        String elemStr = elemProperties.toString();
        for(int i=0; i<arrayList.size(); i++){
            String tempStr = arrayList.get(i).toString();
            if(tempStr.equals(elemStr)){
                return true;
            }
        }
        return false;
    }

    /**
     * 把一个Object转换成JsonSchema对象
     *@param valueobj: 待转换的Object,是从JsonObject.get(key)得来的
     *@return JSONObject
     */
    public static JSONObject transObj2Schema(Object valueobj)throws JSONException{
        String valueType = getTypeOfObject(valueobj);
        JSONObject properties = new JSONObject();
        JSONObject returnJsonObj = new JSONObject();
        returnJsonObj.put(JsonSchemaUtils.properties,properties);
        JSONObject range = new JSONObject();
        properties.put("valueType",valueType);
        properties.put("required",true);
        properties.put("canNull",true);    //值是否可以为null

//        if(valueobj.equals(null)){
//            return returnJsonObj;
//        }
        switch(valueType){
            case "array":
                range.put("minLen",0);
                range.put("maxLen",Integer.MAX_VALUE);
                properties.put("range",range);

                JSONArray jArray = (JSONArray)valueobj;
                JSONArray arrayList = new JSONArray();
                JSONObject tempArraySchema ;
                for(int i=0; i<jArray.size(); i++){
                    Object element = jArray.get(i);
                    tempArraySchema = transObj2Schema(element);
                    arrayList.add(tempArraySchema);
                }
                properties.put("listType",arrayList);
                break;
            case "Object":
                JSONObject tempObjectSchema ;
                Iterator it =((JSONObject) valueobj).keySet().iterator();
                while(it.hasNext()){
                    String key = (String)it.next();
                    Object value = ((JSONObject)valueobj).get(key);
                    tempObjectSchema=transObj2Schema(value);
                    returnJsonObj.put(key,tempObjectSchema);
                }
                break;
            case "String":
                JSONArray arry = new JSONArray();
                String str = (String)valueobj;
                arry.add("");
                range.put("canEmpty",true);
                if(isNumString(str)){
                    range.put("canEmpty",false);
                    range.put("isNumString",true);
                    range.put("min",0);
                    range.put("max",Integer.MAX_VALUE);
                    properties.put("range",range);
                }else {
                    range.put("canEmpty",true);
                    range.put("minLen", 0);
                    range.put("maxLen", 65534);
                    range.put("start", arry);
                    range.put("end", arry);
                    range.put("regex", "^*");
                    properties.put("range", range);
                }

                break;
            case "Double":
                range.put("min",0.0);
                range.put("max",Double.MAX_VALUE);
                properties.put("range",range);
                break;
            case "Integer":
                range.put("min",0);
                range.put("max",Integer.MAX_VALUE);
                properties.put("range",range);
                break;
            case "Boolean":
                properties.put("range",range);
                break;
            case "Null":
                properties.put("range",range);
            default:
                break;
        }

        return returnJsonObj;
    }
    /**
     * 把json字符串转换成JsonSchema
     *@param jsonStr:String 待转换字符串
     *@return jsonStr:转换后的JsonSchema
     *@throw JSONException
     */
    public static JSONObject jsonString2ObjectSchema(String jsonStr) throws JSONException{
        JSONObject schemaObj = new JSONObject();
        JSONObject strObj;
        try{
            strObj = JSON.parseObject(jsonStr);
        }catch(JSONException e){
            LoggerFactory.getLogger(Utils.class).error("some error occur when transfer json str to jsonObject:" + e.getMessage());
            return schemaObj;
        }
        schemaObj = transObj2Schema(strObj);
        return schemaObj;
    }

    /**
     * 判断数组中的某一个元素的格式是否存在
     */
    public static String elemInArray(String elemString,JSONObject jsonSchemaArray) throws JSONException
    {
        JSONObject json = JSON.parseObject(elemString);
        String elemTypeString = getTypeOfObject(json);
        Iterator iterator = jsonSchemaArray.keySet().iterator();
        String key = null;
        Object value = null;
        int flag = 1;
        while(iterator.hasNext())
        {
            key = (String)iterator.next();
            value = jsonSchemaArray.get(key);
            String valueString = jsonSchemaArray.getString(key);
            if(elemTypeString.equals(getTypeOfObject(value)))
            {
                Iterator it = json.keySet().iterator();
                String elemKey = null;
                Object elemValue = null;
                while(it.hasNext())
                {
                    elemKey = (String)it.next();
                    elemValue = json.get(elemKey);
                    JSONObject newValueArray =JSON.parseObject(valueString);
                    if(newValueArray.containsKey(elemKey))
                    {
                        if(!getTypeOfObject(elemValue).equals("Object"))
                        {
                            return key;
                        }
                        else
                        {
                            String elemValueString = json.getString(elemKey);
                            if(elemInArray(elemString,newValueArray).equals(""))
                            {
                                return key;
                            }
                            else
                            {
                                return "";
                            }
                        }

                    }
                    else
                    {
                        return "";
                    }
                }
            }
            else
            {
                continue;
            }
        }
        return "";
    }


    /**
     * 实现获取对象的类型
     * @param object :对象
     * @return :对象类型
     */
    public static  String getTypeOfObject(Object object)
    {
        if (object ==null){
            return "Null";
        }else {
            String type = object.getClass().getName();
            if (type.equals("com.alibaba.fastjson.JSONObject") || type.equals("org.json.JSONObject") || type.equals("org.json.JSONObject$Null")) {
                return "Object";
            } else if (type.equals("com.alibaba.fastjson.JSONArray")) {
                return "array";
            } else if (type.equals("java.lang.String")) {
                return "String";
            } else if (type.equals("java.lang.Double")) {
                return "Double";
            } else if (type.equals("java.lang.Integer") || type.equals("java.lang.Long")) {
                return "Integer";
            } else if (type.equals("java.lang.Boolean")) {
                return "Boolean";
            }

        }
        return "unknow";
    }

    /**
     *说 明:把后一个JsonObject和SchemaObj对比,
     *@param jsonSchemaObj:JSONObject 模板对象
     *@param actualObj:Object 实际的JsonObject
     *@return JSONObject:包含错误key的JsonObject
     *@throw JSONException
     */
    public static JSONObject matchJsonObjWithSchema(JSONObject jsonSchemaObj,Object actualObj)throws JSONException {
        JSONObject resultObj = new JSONObject() ;
        try{
            JSONObject properties = (JSONObject) jsonSchemaObj.get(JsonSchemaUtils.properties);
            String valueType = (String) properties.get("valueType");  //获取类型
            String actualType = getTypeOfObject(actualObj);

            if (actualType.equals(null) && !valueType.equals(null))   //必须先判断是否可以为null,因为null是一个Object,在类型判断时,可能会出错
            {
                if (!(Boolean) properties.get("canNull")) {
                    resultObj.put("canNull", "can not Null,but Null");   //判断是否为null
                    return resultObj;
                }
                return resultObj;
            }

            if (!valueType.equals(actualType)) {    //类型错误
                resultObj.put("type", "type error,must " + valueType + "; but " + actualType);
                return resultObj;
                //result = "failure";
            }

            JSONObject range = new JSONObject();
            if (!valueType.equals("Object"))
                range = (JSONObject) properties.get("range");  //获取范围

            switch (valueType) {
                case "array":
                    JSONArray jArray = (JSONArray) actualObj;
                    int length = jArray.size();    //校验数组长度
                    if (length < (int) range.get("minLen")) {
                        resultObj.put("length", "<" + (Integer) range.get("minLen"));
                        schemaResult = "failure";
                    }
                    if (length > (int) range.get("maxLen")) {
                        resultObj.put("length", ">" + (Integer) range.get("maxLen"));
                        schemaResult = "failure";
                    }
                    JSONArray valueArray = (JSONArray) properties.get("listType");
                    Boolean matchResult = false;
                    for (int i = 0; i < length; i++) {
                        Object arrObj = jArray.get(i);
                        JSONObject temp;
                        JSONObject valueObj = (JSONObject) valueArray.get(i);
                        temp = matchJsonObjWithSchema(valueObj, arrObj);

                        if (temp.size() == 0) {
                            matchResult = true;
                        }
                        if (matchResult == false) {
                            resultObj.put(Integer.toString(i), "array[" + i + "] not in schemaArray");
                            schemaResult = "failure";
                        }
                    }
                    break;

                case "Object":
                    Iterator resultIterator = jsonSchemaObj.keySet().iterator();
                    JSONObject jsonObject = (JSONObject) actualObj;
                    while (resultIterator.hasNext()) {
                        String key = (String) resultIterator.next();
                        if (!key.equals(JsonSchemaUtils.properties)) {
                            JSONObject value = (JSONObject) jsonSchemaObj.get(key);
                            Boolean required = (Boolean) properties.get("required");
                            if (required && !jsonObject.containsKey(key)) {   //判断字段必须存在但实际不存在
                                resultObj.put(key, "no exist");
                                schemaResult = "failure";
                                continue;
                            }
                            JSONObject temp;
                            temp = matchJsonObjWithSchema(value, jsonObject.get(key));
                            if (temp.size() != 0) {
                                resultObj.put(key, temp);
                                schemaResult = "failure";
                            }
                        }
                        jsonObject.remove(key); //该节点检查过,删除掉
                    }
                    break;
                case "String":
                    String str = (String) actualObj;

                    Boolean canEmpty = (Boolean) range.get("canEmpty");

                    if (!canEmpty && str.isEmpty()) {
                        resultObj.put("value", "must has value,but no");
                        schemaResult = "failure";
                    } else {
                        if (range.containsKey("isNumString")) {
                            try {
                                int iStr = Integer.parseInt(str);

                            } catch (Exception e) {
                                break;

                            }
                            int iStr = Integer.parseInt(str);
                            if (iStr < (int) range.get("min")) {
                                resultObj.put("value", "<" + (int) range.get("min"));
                                schemaResult = "failure";
                            }
                            if (iStr > (int) range.get("max")) {
                                resultObj.put("value", ">" + (int) range.get("max"));
                                schemaResult = "failure";
                            }
                        } else {
                            int len = str.length();
                            if (len < (int) range.get("minLen")) {
                                resultObj.put("length", "<" + (Integer) range.get("minLen"));
                                schemaResult = "failure";
                            }
                            if (len > (int) range.get("maxLen")) {
                                resultObj.put("length", ">" + (Integer) range.get("maxLen"));
                                schemaResult = "failure";
                            }

                            JSONArray tempStart = (JSONArray) range.get("start");
                            JSONArray tempEnd = (JSONArray) range.get("end");
                            Boolean sBool = false;  //str是否以数组中某一个字符串开头
                            Boolean eBool = false;  //str是否以数组中某一个字符串结尾
                            for (int s = 0; s < tempStart.size(); s++) {
                                String stFlag = (String) tempStart.get(s);
                                if (str.startsWith(stFlag)) {
                                    sBool = true;
                                }
                            }
                            for (int e = 0; e < tempEnd.size(); e++) {
                                String edFlag = (String) tempEnd.get(e);
                                if (str.endsWith(edFlag)) {
                                    eBool = true;
                                }
                            }
                            if (!sBool) {
                                resultObj.put("StartsWith", false);
                                schemaResult = "failure";
                            }

                            if (!eBool) {
                                resultObj.put("EndstWith", false);
                                schemaResult = "failure";
                            }

                            //正则表达式匹配
                            String pattern = (String) range.get("regex");
                            Pattern r = Pattern.compile(pattern);
                            Matcher match = r.matcher(str);
                            if (!match.find()) {
                                resultObj.put("regex", "no match " + pattern);
                            }
                        }
                    }
                    break;
                case "Double":
                    try {
                        double doub = (double) actualObj;
                        double minDouble = ((Integer) (range.get("min"))).doubleValue();

                        if (doub < minDouble) {
                            resultObj.put("value", "<" + minDouble);
                            schemaResult = "failure";
                        }
                        if (doub > (double) range.get("max")) {
                            resultObj.put("value", ">" + (double) range.get("max"));
                            schemaResult = "failure";
                        }
                        break;
                    } catch (Exception e) {
                        LoggerFactory.getLogger(JsonSchemaUtils.class).
                                error("not a legal Double type");
                        resultObj.put("error", " "+actualObj.toString()+ " is not a legal Double type");
                        schemaResult = "failure";
                    }
                case "Integer":

                    try {
                        int in = (int) actualObj;
                        if (in < (int) range.get("min")) {
                            resultObj.put("value", "<" + (int) range.get("min"));
                            schemaResult = "failure";
                        }
                        if (in > (int) range.get("max")) {
                            resultObj.put("value", ">" + (int) range.get("max"));
                            schemaResult = "failure";
                        }
                        break;
                    } catch (Exception e) {
                        LoggerFactory.getLogger(JsonSchemaUtils.class).
                                error("not a legal Integer type");
                        resultObj.put("error", " "+ actualObj.toString()+ " is not a legal Integer type");
                        schemaResult = "failure";
                    }

                case "Boolean":
                    break;

                case "Null":
                    break;

                default:
                    break;
            }
        }catch (Exception e){
            resultObj.put("error", " not match to the legal schema file");
            schemaResult = "failure";

        }
        return resultObj;
    }

    /**
     * 说 明: 把待匹配json转换为jsonSchema,然后与预期jsonSchema进行比较,把匹配成功的节点和空节点删除,将匹配后的jsonObject返回
     * @param actualJson :实际的json数据
    // * @param jsonSchemaPath :预期jsonSchema的文件名
     * @return :两者的匹配结果
     * @throws JSONException
     */
    public static JSONObject matchJsonStringWithSchema(String actualJson,String jsonSchema)throws JSONException
    {
        JSONObject printResultMessage;
        JSONObject jsonDemo = JSON.parseObject(jsonSchema);
        JSONObject actualJsonObj = JSON.parseObject(actualJson);
        printResultMessage = matchJsonObjWithSchema(jsonDemo,actualJsonObj);
        if(actualJsonObj.size() != 0){
            printResultMessage.put("Extra",actualJsonObj);
            schemaResult = "failure";
        }
        printResultMessage.put("schemaResult",schemaResult);
        return printResultMessage;
    }


    /**
     * 说 明: 把存储json的文件转换成jsonSchema文件
     * @param jsonPath :要转换成Schema文件的json文件绝对路径
     * @param jsonSchemaPath 转换成Schema文件的绝对路径
     * @return 是否写入成功
     * @throws JSONException
     */
    public static Boolean transJsonStringtoSchema(String jsonPath,String jsonSchemaPath) throws JSONException {
        String fileStr = getJsonFile(jsonPath);
        JSONObject resultJson ;
        resultJson=jsonString2ObjectSchema(fileStr);
        String jsonString = formatJson(resultJson.toString());
        try {
            FileUtils.writeStringToFile(jsonString, new File(jsonSchemaPath), false);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     *说 明: 判断一个字符串是否是数字字符串
     *@param numStr:字符串
     *@return Boolean:true/false
     *@throw
     */
    public static Boolean isNumString(String numStr){
        if(getTypeOfObject(numStr) != "String")
            return false;
        else{
            try{
                Integer.parseInt(numStr);
            }catch(Exception e){
                return false;
            }
        }
        return true;
    }

    /**
     *说 明: 把JSON字符串格式化
     *@param jsonStr:待格式化的JSON字符串
     *@return String:格式化后的JSON字符串
     *@throw
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * 添加space
     * @param sb
     * @param indent
     */
    public static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

}
