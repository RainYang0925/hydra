package com.xiaohongshu.automation.config;

import java.io.File;
/**
 * @author lingxue created on 6/15/16
 * @version v0.1
 **/

public class GenericConfig {

    public static final String USER_HOME = System.getProperty("user.dir");
    private static final String CONFIG_FILE = "common.properties";
    private static final String TESTCASE_HOME = "testcase.folder.home";
    private static final String MWP_ENV="mwp.common.env";

    private static final String[] configFiles= new String[]{
            "appium.properties","jdbc.env.properties","python.properties","redmine.properties","webDriverClient.properties"
    };

    public static GenericConfig INSTANCE = new GenericConfig();

    private String casehome;
    private String mwpEnv;

    private PropertiesReader config = new PropertiesReader(GenericConfig.CONFIG_FILE,true);
    public String getCasehome(){
        return casehome;
    }

    private GenericConfig(){

        for (String file:configFiles
             ) {
            config.importConfigFile(file);
        }
        //PropertiesReader pr = new PropertiesReader(GenericConfig.CONFIG_FILE);
        casehome =  USER_HOME + File.separator  +  config.loadValue(TESTCASE_HOME) + File.separator ;
        mwpEnv = config.loadValue(MWP_ENV);

    }

    public String getConfig(String key){
        return config.loadValue(key);
    }
    public String getMwpEnv(){
        return mwpEnv;
    }
}
