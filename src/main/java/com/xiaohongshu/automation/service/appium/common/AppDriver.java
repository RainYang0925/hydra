package com.xiaohongshu.automation.service.appium.common;

/**
 * Created by yuanfei on 16/5/19.
 */

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.xiaohongshu.automation.service.appium.page.Page;
import com.xiaohongshu.automation.utils.FileUtils;
import com.xiaohongshu.automation.config.PropertiesReader;
import com.xiaohongshu.automation.utils.Utils;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class AppDriver {

    private static AppiumPara appiumPara = new AppiumPara();
    private static AppDriver driver = new AppDriver();
    private static AppiumDriver webDriver;
    private static DesiredCapabilities capabilities = new DesiredCapabilities();
    private Page page;


    /*
    ** 设置公共的capability
     */
    public static void setCapabilities(){

        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, appiumPara.getVersion());
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, appiumPara.getDevice());
        capabilities.setCapability(MobileCapabilityType.APP,appiumPara.getAppPath());
        capabilities.setCapability("waitForAppScript"," $.delay(5000); $.acceptAlert()");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,"999999");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);

        switch(appiumPara.getOs()){
            case "ios":
                capabilities.setCapability(IOSMobileCapabilityType.SEND_KEY_STRATEGY, "grouped");
                LoggerFactory.getLogger(AppDriver.class).info("Desired Capabilities has been set successfully for ios");
                break;
            case "android":
                capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,appiumPara.getAppPackage() );
                capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appiumPara.getApppActivity());
                capabilities.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD,true);
                capabilities.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, true);
                LoggerFactory.getLogger(AppDriver.class).info("Desired Capabilities has been set successfully for android");
                break;
            default:
                LoggerFactory.getLogger(AppDriver.class).info("Desired Capabilities set error");
                break;

        }
    }

    /*
    **获取driver
     */
    public static AppDriver getDriver() throws MalformedURLException {
        if(webDriver != null){
            return driver;
        }
        else{
            setCapabilities();
            switch (appiumPara.getOs()){
                case "ios":
                    webDriver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
                    LoggerFactory.getLogger(AppDriver.class).info("ios driver has been initialized successfully ");
                    break;
                case "android":
                    webDriver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
                    LoggerFactory.getLogger(AppDriver.class).info("android driver has been initialized successfully");
                    break;
                default:
                    LoggerFactory.getLogger(AppDriver.class).info("Unsupported os, should be ios or android ");
            }
        }
        return driver;
    }


    public static boolean isAppLaunched(){
        if(webDriver!=null){
            String name =webDriver.getDeviceTime();
            if(name!=null)
                return true;
            else
                return false;
        }
        return false;
    }

    /*
    ** 加载页面
    ** @param pageName: 页面的名称
     */
    public AppDriver load(String pageName){
        try {
            URL url = Page.class.getResource("/pages/" + pageName + ".json");
            String json = FileUtils.readStringFromFile(new File(url.getPath()));
            page = JSON.parseObject(json, Page.class);
            LoggerFactory.getLogger(AppDriver.class).info(pageName + "has been successful loaded");
        }
        catch (Exception e){
            e.printStackTrace();
            LoggerFactory.getLogger(AppDriver.class).error("Load page:" + pageName + " error");
        }
        return driver;
    }

    /*
      ** 点击页面元素
      ** @param elementID: json中定义的元素id
    */
    public AppDriver click(String elementID){
        if(page != null){
            try{

                String elementPath = page.getElementPathByID(elementID, appiumPara.getOs());
                if(elementPath.length()!=0) {
                    WebElement el = AppElement.getElement(elementPath, webDriver);
                    el.click();
                    LoggerFactory.getLogger(AppDriver.class).info("click " + elementID + " successful");
                }
            }catch (Exception e){
                LoggerFactory.getLogger(AppDriver.class).info("click " + elementID + " error");
                e.printStackTrace();
            }

        }
        return driver;
    }

    /*
        ** 输入字符串
        ** @param elementID: json中定义的元素id,string:输入字符串
      */
    public AppDriver sendKeys(String elementID,String string) {
        if (page != null) {

            try {
                String elementPath = page.getElementPathByID(elementID, appiumPara.getOs());
                if (elementPath.length() != 0) {
                    AppElement.getElement(elementPath, webDriver).click();
                    AppElement.getElement(elementPath, webDriver).clear();
                    AppElement.getElement(elementPath, webDriver).sendKeys(string);
                }
                LoggerFactory.getLogger(AppDriver.class).info(string + " has been send to " + elementID + "successfully");
            } catch (Exception e) {
                LoggerFactory.getLogger(AppDriver.class).info("sendkeys error");
                e.printStackTrace();
            }
        }
        return driver;
    }

    private TouchAction createTap(WebElement element, int duration) {
        TouchAction tap = new TouchAction(webDriver);
        return tap.press(element).waitAction(duration).release();
    }


    private TouchAction createTap(int x,int y, int duration) {
        TouchAction tap = new TouchAction(webDriver);
        return tap.press(x,y).waitAction(duration).release();
    }


    /*
    ** 指定时间tap元素
    ** @param elementID: json中定义的元素id,duration:持续时间
  */
    public AppDriver tap(int fingers, String elementID,int duration){
        if(page!=null) {
            try{
                String elementPath = page.getElementPathByID(elementID, appiumPara.getOs());
                if(elementPath.length()!=0){

                    WebElement el =AppElement.getElement(elementPath, webDriver);
                    MultiTouchAction multiTouch = new MultiTouchAction(webDriver);
                    for(int i = 0; i < fingers; ++i) {
                        multiTouch.add(this.createTap(el, duration));
                    }

                    multiTouch.perform();
                }
                LoggerFactory.getLogger(AppDriver.class).info("tap " + elementID + " successfully");

        }catch(Exception e) {
                LoggerFactory.getLogger(AppDriver.class).info("tap " + elementID + " error");
            e.printStackTrace();
        }
        }
        return driver;
    }

    /*
 ** 指定时间tap指定坐标
 ** @param x: x轴坐标，y: y轴坐标, duration:持续时间
*/
    public AppDriver tap(int fingers, int x,int y,int duration){
        try {
            MultiTouchAction multiTouch = new MultiTouchAction(webDriver);
            for (int i = 0; i < fingers; i++) {
                multiTouch.add(this.createTap(x, y, duration));
            }
            multiTouch.perform();
            LoggerFactory.getLogger(AppDriver.class).info("tap location with coordinate x =" + x + " y=" + y + " successfully");
        }catch (Exception e){
            LoggerFactory.getLogger(AppDriver.class).info("tap error");
            e.printStackTrace();
        }
        return driver;
    }


    /*
** tap某一元素的指定位置
** @param elementID: json中定义的元素id，count: 元素被划分的个数，locate:想要点击的元素排位
*/
    public AppDriver tap(String elementID,int count,int locate){
        if(page!=null) {
            try{
                String elementPath = page.getElementPathByID(elementID, appiumPara.getOs());
                if(elementPath.length()!=0){
                    WebElement el =AppElement.getElement(elementPath, webDriver);
                    MultiTouchAction multiTouch = new MultiTouchAction(webDriver);
                    int x = el.getLocation().getX();
                    int y = el.getLocation().getY();
                    Dimension dimensions = el.getSize();
                    int centerY = y + dimensions.getHeight()/2;
                    int index = (int) ((x+dimensions.getWidth()) * (locate-0.5)/count);
                    System.out.println(index + " " + centerY);
                    multiTouch.add(this.createTap(index, centerY, 3));
                    multiTouch.perform();
                }
                LoggerFactory.getLogger(AppDriver.class).info("tap " + elementID + " at the location " + locate + "/" + count);
             }catch(Exception e){
                LoggerFactory.getLogger(AppDriver.class).info("tap error");
                 e.printStackTrace();
            }
        }

        return driver;
    }


    /*
** 滑动到指定文字
** @param text: 指定文字
*/
//    public AppDriver scrollsTo(String text){
//        switch (appiumPara.getOs()){
//            case "ios":
//                IOSDriver iosDriver = (IOSDriver) webDriver;
//                iosDriver.scrollTo(text);
//                Logger.info("scroll to "+ text+ " successfully");
//                break;
//            case "android":
//                AndroidDriver androidDriver = (AndroidDriver) webDriver;
//                androidDriver.scrollTo(text);
//                Logger.info("scroll to " + text + " successfully");
//                break;
//            default:
//                break;
//
//        }
//        return driver;
//    }

    /*
** 在系统弹出框中点击接受 ios专用
*/

    public AppDriver switchToAccept(){
        RemoteWebDriver remoteWebDriver =  webDriver;
        remoteWebDriver.switchTo().alert().accept();
        LoggerFactory.getLogger(AppDriver.class).info("alert has been accepted");
        return driver;
    }

    /*
** 在系统弹出框中点击忽略  ios专用
*/
    public AppDriver switchToDismiss(){
        RemoteWebDriver remoteWebDriver =  webDriver;
        Utils.waitTime(10);
        remoteWebDriver.switchTo().alert().dismiss();
        LoggerFactory.getLogger(AppDriver.class).info("alert has been dismissed");
        return driver;
    }

    public enum DirectionType {
        UP, DOWN, LEFT, RIGHT
    }
    /*
** 在指定元素上按照时间时间进行指定方向的滑动
* ** @param elementID: json中定义的元素id，direction: 方向，duration:指定时间
*/

    public AppDriver swipe(String elementID,String direction,int duration){
        if(page!=null){
            try {
                String elementPath = page.getElementPathByID(elementID, appiumPara.getOs());
                if (elementPath.length() != 0) {
                    WebElement el = AppElement.getElement(elementPath, webDriver);
                    switch (appiumPara.getOs()){
                        case ("ios"):
                            driver.swipeIOS(el,direction,duration,elementID);
                            break;
                        case ("android"):
                            driver.swipeAndroid(el,direction,duration,elementID);
                            break;
                        default:
                            break;

                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return driver;
    }

    /*
** android中在指定元素按照时间时间进行指定方向的滑动
* ** @param elementID: json中定义的元素id，direction: 方向，duration:指定时间
*/
    public AppDriver swipeAndroid(WebElement  element,String direction,int duration,String elementID){

        int x = element.getLocation().getX();
        int y = element.getLocation().getY();
        Dimension dimensions = element.getSize();
        int startX =1;
        int startY=1;
        int endX=1;
        int endY=1;
        switch (DirectionType.valueOf(direction)){
            case UP:
                startX = x+dimensions.getWidth()/2;
                startY = y + dimensions.getHeight();
                endX = x+dimensions.getWidth()/2;
                break;
            case DOWN:
                startX = x+dimensions.getWidth()/2;
                endX = x+dimensions.getWidth()/2;
                endY = y+dimensions.getHeight();
                break;
            case LEFT:
                startX = x+ dimensions.getWidth();
                startY = y+ dimensions.getHeight()/2;
                endY = y+ dimensions.getHeight()/2;
                break;
            case RIGHT:
                startY = y + dimensions.getHeight()/2;
                endX = x +dimensions.getWidth();
                endY = y + dimensions.getHeight()/2;
                break;
            default:
                break;
        }
        try {
            driver.swipe(startX, startY, endX, endY, duration);
            LoggerFactory.getLogger(AppDriver.class).info("swipe " + elementID + " " + DirectionType.valueOf(direction) + " successfully");
        }catch (Exception e){
            LoggerFactory.getLogger(AppDriver.class).info("swipe " + elementID + " " + DirectionType.valueOf(direction) + " error");
            e.printStackTrace();
        }


        return driver;

    }
    /*
**iOS中在指定元素按照时间时间进行指定方向的滑动
* ** @param elementID: json中定义的元素id，direction: 方向，duration:指定时间
*/
    public AppDriver swipeIOS (WebElement element,String direction, int duration,String elementID) {

        MobileElement mobileElement = (MobileElement) element;
        SwipeElementDirection direct =null;
        switch (DirectionType.valueOf(direction)){
            case UP:
                direct =  SwipeElementDirection.UP;
                break;
            case DOWN:
                direct = SwipeElementDirection.DOWN;
                break;
            case LEFT:
                direct = SwipeElementDirection.LEFT;
                break;
            case RIGHT:
                direct = SwipeElementDirection.RIGHT;
                break;
            default:
                break;
        }
        try {
            mobileElement.swipe(direct, duration);
            LoggerFactory.getLogger(AppDriver.class).info("swipe " + elementID + " " + DirectionType.valueOf(direction) + " successfully");
        }catch (Exception e){
            LoggerFactory.getLogger(AppDriver.class).info("swipe " + elementID + " " + DirectionType.valueOf(direction) + " error");
            e.printStackTrace();
        }

        return driver;
    }




    /*
** 在指定元素上按照时间进行指定方向的滑动
* ** @param startx: 开始的x轴坐标，starty:开始点的y轴坐标,endx:停止点的x轴坐标,
* *  endy:停止点的y轴坐标,direction: 方向，duration:指定时间
*/
    public AppDriver swipe(int startx, int starty, int endx, int endy, int duration){
        TouchAction touchAction = new TouchAction(webDriver);
        touchAction.press(startx, starty).waitAction(duration).moveTo(endx, endy).release();
       // touchAction.press(startx, starty).moveTo(endx, endy).waitAction(duration).release();
        touchAction.perform();
        return driver;
    }


    /*
** 获取屏幕截图
*/
    public static void getScreenShot(){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentPath = System.getProperty("user.dir");
        String screenFileName = df.format(new Date())+".png";
        File f = ((TakesScreenshot) (new Augmenter().augment(webDriver)))
                .getScreenshotAs(OutputType.FILE);
        String savePath = currentPath +"/"+screenFileName;
        FileUtils.copyFile(f, new File(savePath));
        LoggerFactory.getLogger(AppDriver.class).info("take a screen shot successfully");
    }

    /*
** 找到指定元素
* ** @param elementID: json中定义的元素id
*/
    public WebElement findElement(String elementID){
        WebElement el =null;
        try {
            String elementPath = page.getElementPathByID(elementID, appiumPara.getOs());
            if(elementPath.length()!=0){

                el = AppElement.getElement(elementPath, webDriver);
                return el;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return el;

    }

    /*
** 判断某元素是否显示
* ** @param elementID: json中定义的元素id
*/
    public boolean isDisplayed(String elementID){

        WebElement el = driver.findElement(elementID);
        boolean b =el.isDisplayed();
        if(b==true){

            LoggerFactory.getLogger(AppDriver.class).info(elementID + " is displayed");
        }
        else{
            LoggerFactory.getLogger(AppDriver.class).info(elementID + "is not displayed");
        }
        return b;
    }

    public boolean isDisplayed(WebElement element){

        boolean b =element.isDisplayed();
        return b;
    }

    /*
** 获取指定元素的text属性值
* ** @param elementID: json中定义的元素id
*/
    public String getText(String elementID){
        WebElement el = driver.findElement(elementID);
        String text = el.getText();
        return text;
    }


    /*
** 等待指定时间，等待指定元素出现
* ** @param elementID: json中定义的元素id, seconds: 指定时间
*/
    public AppDriver waitForDisplayed(String elementID,int seconds) {
        String elementPath;
        String os = appiumPara.getOs();
        elementPath = page.getElementPathByID(elementID,os);
        WebElement el;
        WebDriverWait wait = new WebDriverWait(webDriver, seconds);
        if (elementPath.length() != 0) {
            el = AppElement.getElement(elementPath, webDriver);
            try {
                wait.until(ExpectedConditions.visibilityOf(el));
            }catch (Exception e){
                LoggerFactory.getLogger(AppDriver.class).info("element is not displayed after " + seconds);
            }

        }
        return driver;
    }

    /*
** 收缩指定元素
* ** @param elementID: json中定义的元素id
*/
    public AppDriver pinch(String elementID){
        try{
            WebElement el ;
            String elementPath = page.getElementPathByID(elementID, appiumPara.getOs());
            if(elementPath.length()!=0){
                el = AppElement.getElement(elementPath, webDriver);
                MultiTouchAction multiTouch = new MultiTouchAction(webDriver);
                Dimension dimensions = el.getSize();
                Point upperLeft = el.getLocation();
                Point center = new Point(upperLeft.getX() + dimensions.getWidth() / 2, upperLeft.getY() + dimensions.getHeight() / 2);
                int yOffset = center.getY() - upperLeft.getY();
                TouchAction action0 = (new TouchAction(webDriver)).press(el, center.getX(), center.getY() - yOffset).moveTo(el).release();
                TouchAction action1 = (new TouchAction(webDriver)).press(el, center.getX(), center.getY() + yOffset).moveTo(el).release();
                multiTouch.add(action0).add(action1);
                multiTouch.perform();
            }
            LoggerFactory.getLogger(AppDriver.class).info("pinch " + elementID + " successfully");

        }catch (Exception e){
            e.printStackTrace();
        }
        return driver;
    }

    /*
** 收缩指定元素
* ** @param x: x轴坐标，y:y轴坐标
*/
    public AppDriver pinch(int x,int y){
        MultiTouchAction multiTouch = new MultiTouchAction(webDriver);
        int scrHeight = webDriver.manage().window().getSize().getHeight();
        int yOffset = 100;
        if(y - 100 < 0) {
            yOffset = y;
        } else if(y + 100 > scrHeight) {
            yOffset = scrHeight - y;
        }

        TouchAction action0 = (new TouchAction(webDriver)).press(x, y - yOffset).moveTo(x, y).release();
        TouchAction action1 = (new TouchAction(webDriver)).press(x, y + yOffset).moveTo(x, y).release();
        multiTouch.add(action0).add(action1);
        multiTouch.perform();
        LoggerFactory.getLogger(AppDriver.class).info("pinch " + x + y + " successfully");
        return driver;
    }

    /*
** 放大指定元素
* ** @param elementID: json中定义的元素id
*/
    public AppDriver zoom(String elementID){
        try{
            WebElement el ;
            String elementPath = page.getElementPathByID(elementID, appiumPara.getOs());
            if(elementPath.length()!=0){
                el = AppElement.getElement(elementPath, webDriver);
                MultiTouchAction multiTouch = new MultiTouchAction(webDriver);
                Dimension dimensions = el.getSize();
                Point upperLeft = el.getLocation();
                Point center = new Point(upperLeft.getX() + dimensions.getWidth() / 2, upperLeft.getY() + dimensions.getHeight() / 2);
                int yOffset = center.getY() - upperLeft.getY();
                TouchAction action0 = (new TouchAction(webDriver)).press(center.getX(), center.getY()).moveTo(el, center.getX(), center.getY() - yOffset).release();
                TouchAction action1 = (new TouchAction(webDriver)).press(center.getX(), center.getY()).moveTo(el, center.getX(), center.getY() + yOffset).release();
                multiTouch.add(action0).add(action1);
                multiTouch.perform();
            }
            LoggerFactory.getLogger(AppDriver.class).info("zoom " + elementID + " successfully");

        }catch (Exception e){
            e.printStackTrace();
        }

        return driver;
    }

        /*
** 放大指定元素
* ** @param elementID: json中定义的元素id
*/
    public AppDriver zoom(int x, int y){
        MultiTouchAction multiTouch = new MultiTouchAction(webDriver);
        int scrHeight = webDriver.manage().window().getSize().getHeight();
        int yOffset = 100;
        if(y - 100 < 0) {
            yOffset = y;
        } else if(y + 100 > scrHeight) {
            yOffset = scrHeight - y;
        }

        TouchAction action0 = (new TouchAction(webDriver)).press(x, y).moveTo(0, -yOffset).release();
        TouchAction action1 = (new TouchAction(webDriver)).press(x, y).moveTo(0, yOffset).release();
        multiTouch.add(action0).add(action1);
        multiTouch.perform();
        LoggerFactory.getLogger(AppDriver.class).info("zoom " + x + y + " successfully");
        return driver;
    }
    /*
    ** 模拟设备摇晃 ios专用
     */
    public AppDriver shake(){
        IOSDriver iosDriver = (IOSDriver) webDriver;
        iosDriver.execute("shake", ImmutableMap.of("seconds", Integer.valueOf(5)));
        LoggerFactory.getLogger(AppDriver.class).info("shake device successfully");
        return driver;
    }
    /*
    **退出driver
     */
    public void quit(){
        if(webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
        LoggerFactory.getLogger(AppDriver.class).info("driver quit successfully");
    }
    /*
** 关APP
*/
    public AppDriver closeApp(){
        webDriver.closeApp();
        LoggerFactory.getLogger(AppDriver.class).info("app has been closed successfully");
        return driver;
    }
    /*
** 启动app
*/
    public AppDriver launchApp(){
        webDriver.launchApp();
        LoggerFactory.getLogger(AppDriver.class).info("app has been launched successfully");
        return driver;
    }

    /*
** 将当前app运行至后台
*/
    public AppDriver runAppInBackground(int seconds){
        LoggerFactory.getLogger(AppDriver.class).info("app begin to run in the background for " + seconds + " seconds");
        webDriver.execute("runAppInBackground", ImmutableMap.of("seconds", Integer.valueOf(seconds)));
        LoggerFactory.getLogger(AppDriver.class).info("run in the background finished ");

        return driver;
    }
    /*
  ** 安装指定APP
  * @param appPath: app的路径
  */
    public AppDriver installApp(int appPath){
        webDriver.execute("installApp", ImmutableMap.of("appPath", appPath));
        return driver;
    }
    /*
    ** 锁定屏幕指定时间
    * @param seconds: 指定时间
    */
    public AppDriver lockDevice(int seconds){
        LoggerFactory.getLogger(AppDriver.class).info("device begin to locked for " + seconds + " seconds");
        webDriver.execute("lock", ImmutableMap.of("seconds", Integer.valueOf(seconds)));
        LoggerFactory.getLogger(AppDriver.class).info("lock device finished");

        return driver;
    }
    /*
    ** 设备接触锁定 android专用
    */
    public AppDriver unlockDevice(){
        webDriver.execute("unlock", ImmutableMap.of("seconds", Integer.valueOf(5)));
        LoggerFactory.getLogger(AppDriver.class).info("decive has been unlocked");
        return driver;
    }
    /*
    ** 设备是否被锁定 android专用
    */
    public boolean isLocked(){
        AndroidDriver androidDriver = (AndroidDriver) webDriver;
        Response response = androidDriver.execute("isLocked", ImmutableMap.of("seconds", Integer.valueOf(5)));
        return Boolean.parseBoolean(response.getValue().toString());
    }
    /*
** 收起键盘
*/
    public AppDriver hideKeyboard(){
        webDriver.execute("hideKeyboard", ImmutableMap.of("seconds", Integer.valueOf(5)));
        return driver;
    }
    /*
** 打开下拉通知栏 Android专用
*/
    public AppDriver openNotifications(){
        AndroidDriver androidDriver = (AndroidDriver) webDriver;
        androidDriver.execute("openNotifications", ImmutableMap.of("seconds", Integer.valueOf(5)));
        return driver;
    }
    /*
** 检查应用是否已经安装
* @ param bundleId:应用名
*/
    public boolean isAppInstalled(String bundleId){
        Response response = webDriver.execute("isAppInstalled", ImmutableMap.of("bundleId", bundleId));
        return Boolean.parseBoolean(response.getValue().toString());
    }
    /*
** 删除应用
* * @ param bundleId:应用名
*/
    public AppDriver removeApp(String bundleId){
        try {
            webDriver.execute("removeApp", ImmutableMap.of("bundleId", bundleId));
            LoggerFactory.getLogger(AppDriver.class).info("remove app successfully");
        }catch (Exception e){
            LoggerFactory.getLogger(AppDriver.class).info("remove app error");
        }
        return driver;
    }

    /*
** 卸载重装应用
*/
    public AppDriver resetApp(){
        webDriver.execute("reset", ImmutableMap.of("seconds", Integer.valueOf(30)));
        return driver;
    }

    /*
    ** 获得当前driver类型（iOS或者android）
            */
    public String getDriverType(){
        String os;
        os = new PropertiesReader("appium.properties").loadValue("appium.target.os");
        return os;
    }

    public static ImmutableMap<String, Object> getCommandImmutableMap(String param, Object value) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        builder.put(param, value);
        return builder.build();
    }

    /*
** 通过text获取元素
* @ param commentRoot:元素所在的上级目录，text:text属性值
*/
    public WebElement getWebElementByText(String commentRoot,String text,String text2){
        String elementPath = page.getElementPathByID(commentRoot, appiumPara.getOs());
        String elementText = null;
        WebElement el = null;
        List<WebElement> elements =AppElement.getElements(elementPath, webDriver);
        int size = elements.size();
        // System.out.println(size);
        for(int i=1;i<size+1;i++){
            String findPath=elementPath+"["+i+"]"+"/UIAStaticText";
            //System.out.println(findPath);
            List<WebElement> list =  AppElement.getElements(findPath, webDriver);
            int textSize = list.size();
            //System.out.println(textSize);
            for(int j=0;j<textSize;j++){
                elementText = list.get(j).getText();
                //System.out.println(elementText);
                if(elementText.equals(text) ){
                    if(list.get(j+1).getText().equals(text2)){
                        el=list.get(j);
                        return el;
                    }
                }
            }
        }
        return el;
    }
    /*
** 通过text获取元素
* @ param commentRoot:元素所在的上级目录，text:text属性值
*/
    public WebElement getWebElementByText(String commentRoot,String text){
        String elementPath = page.getElementPathByID(commentRoot, appiumPara.getOs());
        String elementText = null;
        WebElement el = null;
        List<WebElement> elements =AppElement.getElements(elementPath, webDriver);
        int size = elements.size();
       // System.out.println(size);
        for(int i=1;i<size+1;i++){
            String findPath=elementPath+"["+i+"]"+"/UIAStaticText";
            //System.out.println(findPath);
            List<WebElement> list =  AppElement.getElements(findPath, webDriver);
            int textSize = list.size();
            //System.out.println(textSize);
            for(int j=0;j<textSize;j++){
                elementText = list.get(j).getText();
                //System.out.println(elementText);
                if(elementText.equals(text) ){
                    el=list.get(j);
                    return el;
                }
            }
        }
        return el;
    }
    /*
** 给设备发送一个按键事件 android专用
* @ param key:应用名
*/
    public AppDriver pressKeyCode(int key){
        AndroidDriver androidDriver = (AndroidDriver) webDriver;
        androidDriver.execute("pressKeyCode", getCommandImmutableMap("keycode", Integer.valueOf(key)));
        return driver;
    }

    /*
** 获取设备的屏幕方向
*/
    public ScreenOrientation getOrientation(){
       return  webDriver.getOrientation();
    }
    /*
** 更换设备的屏幕方向
* @ param orientation:ScreenOrientation枚举类型，LANDSCAPE，PORTRAIT
*/
    public AppDriver rotate(ScreenOrientation orientation){
        webDriver.rotate(orientation);
        return driver;
    }

    public Set<String> getContextHandles(){
        return webDriver.getContextHandles();
    }
    public String getContext(){
        return webDriver.getContext();
    }

    public WebDriver context(String context){
       return webDriver.context(context);
    }

    public WebDriver switchToWebview(){
        Set<String > set =  driver.getContextHandles();
        String webContext = "";
        if(set.size()>1) {
            for (String context : set) {
                if (!context.contains("NATIVE")) {
                    webContext = context;
                    driver.context(webContext);
                    LoggerFactory.getLogger(AppDriver.class).info("switch to UIAWebview successfully");

                }
            }

        }
        else{
            LoggerFactory.getLogger(AppDriver.class).info("Error! no UIAWebview to switch");

        }
        return (WebDriver) webDriver;

    }
    public AppDriver switchToNative(){
        driver.context("NATIVE_APP");
        return driver;
    }
    /*
** 长按某元素
* @ param elementID:元素ID，duration:持续时间
*/
    public AppDriver longPress(String elementID, int duration){
        if(page!=null){
            try {
                String elementPath = page.getElementPathByID(elementID, appiumPara.getOs());
                if (elementPath.length() != 0) {
                    WebElement el = AppElement.getElement(elementPath, webDriver);
                    TouchAction touchAction = new TouchAction(webDriver);
                    touchAction.longPress(el,duration);
                    LoggerFactory.getLogger(AppDriver.class).info(elementID + " has been long pressed for " + duration + " seconds");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return driver;
    }

    /*
** 获取指定元素的返回值为String类型的属性
* @ param elementID:元素ID，attribute:属性，如text,tagName等
*/
    public String getStringAttribute(String elementID,String attribute){
        String res = "";
        WebElement element = driver.findElement(elementID);
        if(element!=null){
            switch (appiumPara.getOs()){
                case "ios":
                    IOSElement iosElement = (IOSElement) element;
                    switch(attribute){
                        case ("tagName"):
                            res = iosElement.getTagName();
                            break;
                        case ("id"):
                            res= iosElement.getId();
                            break;
                        case ("text"):
                            res = iosElement.getText();
                            break;
                        default:
                            try{
                                res = iosElement.getAttribute(attribute);
                                break;
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                    }
                    break;
                case "android" :
                    AndroidElement androidElement = (AndroidElement) element;
                    switch(attribute){
                        case ("tagName"):
                            res = androidElement.getTagName();
                            break;
                        case ("id"):
                            res= androidElement.getId();
                            break;
                        case ("text"):
                            res = androidElement.getText();
                            break;
                        default:
                            try{
                                res = androidElement.getAttribute(attribute);
                                break;
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                    }
                    default:
                    break;
            }
        }
     return res;
    }
    /*
** 获取指定元素的返回值为Boolean类型的属性
* @ param elementID:元素ID，attribute:属性，如selected,displayed等
*/
    public boolean getBooleanAttribute(String elementID,String attribute){
        Boolean b = false;
        WebElement element = driver.findElement(elementID);
        if(element!=null){
            switch (appiumPara.getOs()){
                case "ios":
                    IOSElement iosElement = (IOSElement) element;
                    switch(attribute){
                        case ("selected"):
                            b = iosElement.isSelected();
                            break;
                        case ("enabled"):
                            b= iosElement.isEnabled();
                            break;
                        case ("displayed"):
                            b = iosElement.isDisplayed();
                            break;
                        default:
                            break;
                    }
                    break;
            case "android" :
                AndroidElement androidElement = (AndroidElement) element;
                switch(attribute){
                    case ("selected"):
                        b = androidElement.isSelected();
                        break;
                    case ("enabled"):
                        b= androidElement.isEnabled();
                        break;
                    case ("displayed"):
                        b = androidElement.isDisplayed();
                        break;
                    default:
                        break;
                }
                default:
                     break;
             }

        }
        return b;
    }

    public AppDriver closeByTapOuter(String elementID,String direction){
        WebElement element = driver.findElement(elementID);
        int x = element.getLocation().getX();
        int y = element.getLocation().getY();
        Dimension dimensions = element.getSize();
        int tapX=1;
        int tapY=1;

        switch (DirectionType.valueOf(direction)){
            case UP:
                tapX = x+dimensions.getWidth()/2;
                tapY = y + dimensions.getHeight()-1;
                break;
            case DOWN:
                tapX = x+dimensions.getWidth()/2;
                tapY = y + dimensions.getHeight()+1;
                break;
            case LEFT:
                tapX = x-1;
                tapY = y + dimensions.getHeight()/2;
                break;
            case RIGHT:
                tapX = x+1;
                tapY = y + dimensions.getHeight()/2;
                break;
            default:
                break;
        }
        driver.tap(1,tapX,tapY,5);
        return driver;
    }

    public static void main(String []args){

        try {

            driver =  AppDriver.getDriver();
            Utils.waitTime(30000);
            Set<String> contextNames = driver.getContextHandles();
            for (String contextName : contextNames) {
                System.out.println(contextNames);
            }
            //driver.runAppInBackground(5);
           // driver.launchApp();
           // driver.load("HomePage").click("chartButton");
//            Utils.waitTime(30000);
//            driver.load("LoginPage");
//            driver.sendKeys("userName","海天一色2002");
//            driver.sendKeys("password","123456");
            //Utils.waitTime(30000);
            //driver.load("LoginPage");
            //driver.swipe("liveSwipe", "RIGHT", 10);
           // Utils.waitTime(30000);
            //driver.getBooleanAttribute("firstTalk", "selected");
//            System.out.println(driver.getBooleanAttribute("liveSwipe","selected"));
//            System.out.println(driver.getBooleanAttribute("liveSwipe","displayed"));
//            System.out.println(driver.getBooleanAttribute("liveSwipe","enabled"));
//            System.out.println(driver.getStringAttribute("liveSwipe","tagName"));
//            System.out.println(driver.getStringAttribute("liveSwipe","text"));
//            System.out.println(driver.getStringAttribute("liveSwipe","id"));
//            System.out.println(driver.getStringAttribute("liveSwipe","label"));
//            System.out.println(driver.getStringAttribute("liveSwipe","value"));
            //Utils.waitTime(10000);
           // driver.swipe("firstTalk","LEFT",10);
          //  Utils.waitTime(10000);
      //       driver.swipeComplete("firstTalk", "LEFT", 10);
//            Utils.waitTime(10000);
            driver.quit();
           //driver.quit();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}