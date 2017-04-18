package com.xiaohongshu.automation.service.appium.common;

import com.xiaohongshu.automation.config.PropertiesReader;
import org.slf4j.LoggerFactory;

/**
 * @author lingxue created on 5/23/16
 * @version v0.1
 **/

public class AppiumPara {

    private String app;
    private String os;
    private String type;
    private String device;
    private String version;
    private String appPath;
    private String appPackage;
    private String apppActivity;

    public String getAppPath() {
        return appPath;
    }

    public String getOs() {
        return os;
    }

    public String getVersion() {
        return version;
    }

    public String getDevice() {
        return device;
    }

    public String getAppPackage() {return appPackage;}

    public String getApppActivity() {
        return apppActivity;
    }

    public AppiumPara(){

        try{
            PropertiesReader reader = new PropertiesReader("appium.properties");
            app = reader.loadValue("appium.target.app");
            os = reader.loadValue("appium.target.os");
            type = reader.loadValue("appium.target.type");
            device = reader.loadValue("appium.target.device.name");
            version = reader.loadValue("appium." + os + ".version");
            appPath = reader.loadValue(app + "." + os + "." + type);
            appPackage = reader.loadValue("mogujie.android.appPackage");
            apppActivity = reader.loadValue("mogujie.android.appActivity");
        }
        catch (Exception e){
            LoggerFactory.getLogger(AppiumPara.class).error("Appium Para initialize error");
            e.printStackTrace();
        }
    }
}