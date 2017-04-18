package com.xiaohongshu.automation.service.webdriver.common;

import java.io.IOException;
import java.net.URLClassLoader;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.xiaohongshu.automation.service.tesla.TeslaTest;
import com.xiaohongshu.automation.utils.PropertiesReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.slf4j.LoggerFactory;


public class DriverFactory {
    // ##driver name can be : chrome | ie | firefox | android | ios | html
    public static final String CHROME = "chrome";
    public static final String IE = "ie";
    public static final String FIREFOX = "firefox";
    public static final String ANDROID = "android";
    public static final String IOS = "ios";
    public static final String IOS_IP = "com.webdriver.ios.ip";
    public static final String IOS_PORT = "com.webdriver.ios.port";
    public static final String HTML = "html";
    public static final String DRIVER_PARAM = "gap.macro.webdriver.defaultdriver";
    public static final String USE_MULTITHREAD = "com.webdriver.usemultithread";
    public static final String CHROMEBINARYPATH = "chrome.binary.path";
    public static final int implictWaitTime = 10;

    public static boolean jsEnabled = true;
    private static FirefoxDriver firefoxInst;
    private static WebDriver chromeInst;
    private static ChromeDriverService chromeService;
    private static InternetExplorerDriver ieInst;
    private static RemoteWebDriver ipadInst;

    public static String driverType = IE; // Added by bpan@hp.com 2012-11-08

    /**
     * Close chromeService.
     *
     * @author bpan@hp.com Date: 2012-11-08
     */
    public synchronized static void closeChromeSerivce() {
        if (chromeService != null) {
            try {
                chromeService.stop();
                chromeService = null;
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    }


    public synchronized static InternetExplorerDriver getIEDriver() {
        if (null == ieInst) {
            ///added by joey.liu for IEWebdriver
//			System.setProperty("webdriver.ie.driver", "C:\\webdriver\\IEDriverServer.exe");
            String IEDriver = "IEDriverServer.exe";
            ClassLoader cl = URLClassLoader.getSystemClassLoader();
            Logger.getLogger(DriverFactory.class.getName()).info(
                    cl.getResource(IEDriver).toString());
            String path = cl.getResource(IEDriver).getPath();
            Logger.getLogger(DriverFactory.class.getName()).info(path);
            System.setProperty("webdriver.ie.driver", path);
            DesiredCapabilities ie = DesiredCapabilities.internetExplorer();
            ///end
            ie.setJavascriptEnabled(jsEnabled);
            ie.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            ieInst = new InternetExplorerDriver(ie);
            // Let firefox quit after testcase finished to run
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        ieInst.quit();
                    } catch (Exception e) {
                        // Do nothing. Just ensure the chromeService has been
                        // stopped.
                    }
                }
            }));

        }
        return ieInst;
    }

    public synchronized static WebDriver getDriver(PageObject po) {
        if (null != po && null != po.driver) {
            return po.driver;
        } else {
            return getDriver();
        }
    }

    public synchronized static FirefoxDriver getFirefoxDriver() {
        LoggerFactory.getLogger(TeslaTest.class).info("----------- getFirefoxDriver !");
        if (null == firefoxInst) {
            LoggerFactory.getLogger(TeslaTest.class).info("----------- Browser=Firefox !");
            DesiredCapabilities firefox = DesiredCapabilities.firefox();
            firefox.setJavascriptEnabled(jsEnabled);
            firefoxInst = new FirefoxDriver(firefox);
            firefoxInst.manage().timeouts()
                    .setScriptTimeout(1L, TimeUnit.MINUTES);
            firefoxInst.manage().timeouts()
                    .pageLoadTimeout(1L, TimeUnit.MINUTES);
            firefoxInst.manage().timeouts()
                    .implicitlyWait(1L, TimeUnit.MINUTES);
            // Let firefox quit after testcase finished to run
            // Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            //
            // @Override
            // public void run() { // TODO Auto-generated method stub
            // try {
            // firefoxInst.quit();
            // } catch (Exception e) { // Do nothing.
            // // Just ensure the chromeService has been // stopped.
            // }
            // }
            // }));

        }

        return firefoxInst;
    }

    @SuppressWarnings("deprecation")
    public synchronized static WebDriver getChromeDriverStandalone() {
        String chromedriver = "chromedriver";
        ClassLoader cl = URLClassLoader.getSystemClassLoader();
        Logger.getLogger(DriverFactory.class.getName()).info(cl.getResource(chromedriver).toString());
        String path = cl.getResource(chromedriver).getPath();
        Logger.getLogger(DriverFactory.class.getName()).info(path);
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, path);

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        PropertiesReader.initialize("webDriverClient.properties");
        String chromeBinaryPath = PropertiesReader.loadValue(CHROMEBINARYPATH);
        if (null != chromeBinaryPath && !"".equals(chromeBinaryPath))
            capabilities.setCapability("chrome.binary", chromeBinaryPath);
        WebDriver driver = new ChromeDriver(capabilities);
        return driver;
    }

    public synchronized static WebDriver getChromeDriver() {

        if (null == chromeService) {
            /**
             * Start the ChromeDriverServer firstly if needed...
             */
            // String path =
            // "C:\\Kintana\\Automation\\selenium-server-2.20.0\\chromedriver.exe";
            String chromedriver = "chromedriver";
            ClassLoader cl = URLClassLoader.getSystemClassLoader();
            Logger.getLogger(DriverFactory.class.getName()).info(
                    cl.getResource(chromedriver).toString());
            String path = cl.getResource(chromedriver).getPath();
            Logger.getLogger(DriverFactory.class.getName()).info(path);
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
                    path);
            chromeService = ChromeDriverService.createDefaultService();
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        chromeService.stop();
                    } catch (Exception e) {
                        // Do nothing. Just ensure the chromeService has been
                        // stopped.
                    }
                }
            }));
            if (!chromeService.isRunning())
                try {
                    chromeService.start();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            try {

                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        if (null == chromeInst) {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();

            chromeInst = (WebDriver) new RemoteWebDriver(
                    chromeService.getUrl(), capabilities);
        }
        return chromeInst;
    }


    /**
     * @return
     * @throws IOException
     * @Todo: need to improved to load the config and decide which driver will
     * be loaded.
     */
    public static WebDriver getDriver() {
        /**
         * Add Initialize Properties method to ensure we get the correct
         * configuration work.
         */
        PropertiesReader.initialize("webDriverClient.properties");
        WebDriver dr = null;

        /**
         * Arthur.Ren @2013-09-29 to adopt for multi-thread driver Factory.
         */
        String isMultiThread = PropertiesReader.loadValue(USE_MULTITHREAD).trim();
        if ("true".equalsIgnoreCase(isMultiThread)) {
            return NewDriverFactory.getInstance().getDriver();
        }

        // bpan@hp.com 2012-11-08
        // String driverType = PropertiesReader.loadValue(DRIVER_PARAM).trim();
        driverType = PropertiesReader.loadValue(DRIVER_PARAM).trim();

        if (null == driverType || "".equals(driverType.trim())) {
            //driverType = FIREFOX;
             driverType = CHROME;
        }

		/*
         * try { if (!FIREFOX.equalsIgnoreCase(driverType)) {
		 * Runtime.getRuntime().exec("tskill " + driverType);
		 * com.hp.ppm.util.log.Logger.info("Close browsers..."); } } catch
		 * (IOException e1) { // TODO Auto-generated catch block //
		 * e1.printStackTrace(); }
		 */

        try {

            if (CHROME.equals(driverType)) {
                isDriverAvailable(chromeInst);
                dr = getChromeDriver();

            } else if (FIREFOX.equals(driverType)) {
                isDriverAvailable(firefoxInst);
                dr = getFirefoxDriver();
            } else if (IE.equals(driverType)) {
                isDriverAvailable(ieInst);
                dr = getIEDriver();
            }
            Logger.getLogger(DriverFactory.class.getName()).info(
                    "The DriverType we get is :" + driverType);
            Logger.getLogger(DriverFactory.class.getName()).info(
                    "The Driver we get is :" + dr);
            dr.manage().timeouts()
                    .implicitlyWait(implictWaitTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dr;
    }

    public static boolean isDriverAvailable(WebDriver wd) {
        boolean ret = true;
        if (null == wd) {
            ret = false;
            return ret;
        }

        try {
            wd.getCurrentUrl();
        } catch (UnreachableBrowserException ube) {
            if (chromeInst == wd) {
                chromeInst = null;
            }
            if (firefoxInst == wd) {
                firefoxInst = null;
            }
            if (ieInst == wd) {
                ieInst = null;
            }
            ret = false;
        }
        return ret;

    }

}
