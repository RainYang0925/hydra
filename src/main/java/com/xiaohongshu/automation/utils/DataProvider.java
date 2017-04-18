package com.xiaohongshu.automation.utils;

import com.csvreader.CsvReader;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author lingxue created on 6/7/16
 * @version v0.1
 **/

public class DataProvider {

    private static String NO_READ = "#";

    private static List<Map<String,String>> data;

    public static List<Map<String,String>> loadCSV(String input){
        data = new ArrayList<>();
        try{
            CsvReader reader = null;
            if(input.endsWith(".csv")) {
                LoggerFactory.getLogger(DataProvider.class).info("----------Enter loadCSV for file: " + input);
                reader = new CsvReader(input, ',', Charset.forName("UTF-8"));
            }
            else{
                reader = CsvReader.parse(input);
            }
            if(reader.readHeaders()) {
                String[] headers = reader.getHeaders();
                while (reader.readRecord()){
                    if(reader.get(0).startsWith(NO_READ)){
                        continue;
                    }
                    Map<String,String> map = new HashMap<String,String>();
                    for(String head : headers){
                        map.put(head,reader.get(head));
                    }
                    data.add(map);
                }
            }
        }
        catch (FileNotFoundException e){
            LoggerFactory.getLogger(DataProvider.class).error("CSV File not Exist:" + input);
            e.printStackTrace();
        }
        catch (IOException e){
            LoggerFactory.getLogger(DataProvider.class).error("IOException:" + e.getMessage());
            e.printStackTrace();
        }

        return data;
    }

    public static Object[][] loadCSV4Testng(String input) {
        String csvData[][] = null;
        try {
            CsvReader reader = null;
            if (input.endsWith(".csv")) {
                LoggerFactory.getLogger(DataProvider.class).info(
                        "----------Enter loadCSV for file: " + input);
                reader = new CsvReader(input, ',', Charset.forName("UTF-8"));
            } else {
                reader = CsvReader.parse(input);
            }
            if (reader.readHeaders()) {
                int headerCount = reader.getHeaderCount();
                String[] headers = reader.getHeaders();
                int lineCount = 0;
                List<String[]> records = new ArrayList<String[]>();
                while (reader.readRecord()) {
                    String[] record = new String[headerCount];
                    int j = 0;
                    for (String head : headers) {
                        String elment = reader.get(head);
                        if (elment != "") {
                            record[j] = elment;
                        }else {
                            record[j] = null;
                        }
                        j++;
                    }
                    records.add(record);
                    lineCount++;
                }
                csvData = new String[lineCount][headerCount];
                for (int i = 0; i < lineCount; i++) {
                    csvData[i] = (String[]) records.get(i);
                }
            }
        } catch (FileNotFoundException e) {
            LoggerFactory.getLogger(DataProvider.class).error(
                    "CSV File not Exist:" + input);
            e.printStackTrace();
        } catch (IOException e) {
            LoggerFactory.getLogger(DataProvider.class).error(
                    "IOException:" + e.getMessage());
            e.printStackTrace();
        }
        return csvData;
    }

    public static void main(String[] args){
        List<Map<String,String>> data =DataProvider.
                loadCSV("/Users/zhanghao/Documents/Work/Automation/Hydra/js/Demo/tool/csv/goodsReleaseNormalTest.csv");
        Iterator<Map<String,String>> iter = data.iterator();
        while(iter.hasNext()){
            Map<String,String> map = iter.next();
            System.out.println(map.get("caseId") + "-" + map.get("desc"));
        }
    }

}
