package com.xiaohongshu.automation.service.webdriver.common;

import java.io.IOException;
import java.net.URLClassLoader;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.xiaohongshu.automation.utils.PropertiesReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.LoggerFactory;


public class NewDriverFactory {

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
	public static final String CHROMEBINARYPATH = "chrome.binary.path";
	public static final int implictWaitTime = 100;
	
	ThreadBasedDriver driver = new ThreadBasedDriver();

	public static boolean jsEnabled = true;
	private static ChromeDriverService chromeService;

	private static NewDriverFactory inst = new NewDriverFactory();

	private NewDriverFactory() {

	}

	public synchronized static void closeChromeSerivce() {
		if (chromeService != null) {
			try {
				chromeService.stop();
			} catch (Exception e) {
				// TODO: handle exception
			}
			chromeService = null;
		}

	}

	public static NewDriverFactory getInstance() {
		return inst;
	}

	public synchronized WebDriver getDriver() {
		WebDriver ret = driver.get();
		if (!isDriverAvailable(ret)){
			ret = createDriver();
			driver.set(ret);
		}
		return ret;
	}
	
	public synchronized void freeDriver(){
		WebDriver wd = driver.get();
		if (isDriverAvailable(wd)){
			try{
				wd.quit();
			}catch(Exception e){
				
			}
			wd = null ;
			
		}
	}

	private synchronized WebDriver createDriver() {
		/**
		 * Add Initialize Properties method to ensure we get the correct
		 * configuration work.
		 */
		PropertiesReader.initialize("webDriverClient.properties");
		WebDriver dr = null;

		String driverType = PropertiesReader.loadValue(DRIVER_PARAM).trim();

		if (null == driverType || "".equals(driverType.trim())) {
			driverType = FIREFOX;
			// driverType = CHROME;
		}
		try {

			if (CHROME.equals(driverType)) {
				
				dr = getChromeDriver();

			} else if (FIREFOX.equals(driverType)) {
				//isDriverAvailable(firefoxInst);
				dr = getFirefoxDriver();
			} else if (IE.equals(driverType)) {
				dr = getIEDriver();
			} else if (ANDROID.equals(driverType)) {
				dr = getAndroidDriver();
			} else if (HTML.equals(driverType)) {
				dr = getHtmlUnitDriver();
			} else if (IOS.equals(driverType)) {
				dr = getIPADDriver();
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

	private WebDriver getIPADDriver() {
		// TODO Auto-generated method stub
		return null;
	}

	private WebDriver getHtmlUnitDriver() {
		// TODO Auto-generated method stub
		return null;
	}

	private WebDriver getAndroidDriver() {
		// TODO Auto-generated method stub
		return null;
	}

	private WebDriver getIEDriver() {
		// TODO Auto-generated method stub
		return null;
	}

	public WebDriver getFirefoxDriver() {

		LoggerFactory.getLogger(NewDriverFactory.class).info("getFirefoxDriver");
		LoggerFactory.getLogger(NewDriverFactory.class).info("Browser=Firefox");
		DesiredCapabilities firefox = DesiredCapabilities.firefox();
		firefox.setJavascriptEnabled(jsEnabled);
		WebDriver firefoxInst = new FirefoxDriver(firefox);
		firefoxInst.manage().timeouts().setScriptTimeout(1L, TimeUnit.MINUTES);
		firefoxInst.manage().timeouts().pageLoadTimeout(1L, TimeUnit.MINUTES);
		firefoxInst.manage().timeouts().implicitlyWait(1L, TimeUnit.MINUTES);
		return firefoxInst;
	}

	public synchronized WebDriver getChromeDriver() {

		WebDriver chromeInst = null;
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
					closeChromeSerivce();
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
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();

		chromeInst = (WebDriver) new RemoteWebDriver(chromeService.getUrl(),
				capabilities);

		return chromeInst;
	}

	public static boolean isDriverAvailable(WebDriver wd) {
		boolean ret = true;
		if (null == wd) {
			ret = false;
			return ret;
		}

		try {
			wd.getCurrentUrl();
		} catch (Exception ube) {
			try {
				wd.quit();
			} catch (Exception e) {

			}
			wd = null;
			ret = false;
		}
		return ret;

	}
}
