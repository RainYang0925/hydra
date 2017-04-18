package com.xiaohongshu.automation.service.db;

import com.xiaohongshu.automation.config.PropertiesReader;
import com.xiaohongshu.automation.utils.DataProvider;
import com.xiaohongshu.automation.utils.FileUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author lingxue created on 8/25/16
 * @version v0.1
 **/

public class DBChecker {

    private final Logger logger = LoggerFactory.getLogger(DBChecker.class);
    private String dbconfig;
    private String sql;
    private String result;
    private List<Map<String,String>> resultList;

    public DBChecker(String propertyFile){
        try{
            PropertiesReader reader = new PropertiesReader(propertyFile);
            dbconfig = reader.loadValue("db");
            sql = reader.loadValue("sql");
            String resultFile = new File(propertyFile).getParent() + "/" + reader.loadValue("result");
            result = FileUtils.readStringFromFile(new File(resultFile));
        }
        catch (Exception e){
            logger.error("Error for propertyFile: " + propertyFile);
        }
    }

    public void replaceSql(String origin,String replace){
        sql = sql.replaceAll(origin,replace);
    }

    public void replaceResultCSV(String origin,String replace){
        result = result.replaceAll(origin,replace);
    }

    public boolean check(){
        try{
            resultList = DataProvider.loadCSV(result);
            Connection connection = DBUtil.getInstance().getConnectionWith(dbconfig);
            Row[] rows =DBUtil.getInstance().executeQuery(connection,sql);
            if(rows.length != resultList.size()){
                logger.error("sql result count not conincident, expect count: " + resultList.size() +
                ", actual count: " + rows.length);
                return false;
            }
            for(int index=0;index<rows.length;index++){
                for(Field field : rows[index].getFields()){
                    if(resultList.get(index).containsKey(field.getName())){
                        if(!(resultList.get(index).get(field.getName()).equals(String.valueOf(field.getValue())))){
                            logger.error("check fail for key: " + field.getName() + ", expect: " +
                                    resultList.get(index).get(field.getName()) + ", actual: " + field.getValue());
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        catch (Exception e){
            logger.error("Exception happen when do db check");
            return false;
        }
    }

    public static void main(String[] args){
        DBChecker checker = new DBChecker("/Users/zhanghao/Documents/Work/Automation/Hydra/src/main/java/com/mogujie/automation/service/db/test.properties");
        checker.replaceSql("//id","1");
        checker.replaceResultCSV("//name","lingxue");
        System.out.println(checker.check());
    }
}
