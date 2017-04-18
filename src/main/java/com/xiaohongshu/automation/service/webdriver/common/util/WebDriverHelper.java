package com.xiaohongshu.automation.service.webdriver.common.util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
/**
 * 
 * @author tianchen
 *
 */
public class WebDriverHelper {
    private static WebDriverWait wait = null;
	    
	private WebDriverHelper () {
		
	}

	/**
	 * 等待Alert框出现, 指定超时时间
	 * @param driver
	 * @param timeOutSeconds
     * @return
     */
    public static Alert waitForAlertIsPresent(WebDriver driver, int timeOutSeconds) {
    	WebDriverWait wait = waitWebDriver(driver, timeOutSeconds);
    	return wait.until(ExpectedConditions.alertIsPresent());
    }

	/**
	 * 等待元素可以被selection的状态为true
	 * @param driver
	 * @param timeOutSeconds
	 * @param element
	 * @param selected
     * @return
     */
	public static boolean waitForElementSelectionStateToBe(WebDriver driver, int timeOutSeconds, WebElement element, boolean selected) {
		WebDriverWait wait = waitWebDriver(driver, timeOutSeconds);
	    return wait.until(ExpectedConditions.elementSelectionStateToBe(element, selected));
	}
	
    public static boolean waitForElementSelectionStateToBe(WebDriver driver, int timeOutSeconds, By locator, boolean selected) {
    	WebDriverWait wait = waitWebDriver(driver, timeOutSeconds);
    	return wait.until(ExpectedConditions.elementSelectionStateToBe(locator, selected));
    }
	
    public static WebElement waitForElementToBeClickable(WebDriver driver, int timeOutSeconds, By locator) {
    	WebDriverWait wait = waitWebDriver(driver, timeOutSeconds);
    	return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    public static boolean waitForElementToBeSelected(WebDriver driver, int timeOutSeconds, By locator) {
    	WebDriverWait wait = waitWebDriver(driver, timeOutSeconds);
    	return wait.until(ExpectedConditions.elementToBeSelected(locator));
    }
    
    public static boolean waitForElementToBeSelected(WebDriver driver, int timeOutSeconds, WebElement element) {
    	WebDriverWait wait = waitWebDriver(driver, timeOutSeconds);
    	return wait.until(ExpectedConditions.elementToBeSelected(element));
    }
    
    public static WebDriver waitForFrameToBeAvailableAndSwitchToIt(WebDriver driver, int timeOutSeconds, String frameLocator) {
    	WebDriverWait wait = waitWebDriver(driver, timeOutSeconds);
    	return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }
    
    public static boolean waitForInvisibilityOfElementLocated(WebDriver driver, int timeOutSeconds, By locator) {
    	WebDriverWait wait = waitWebDriver(driver, timeOutSeconds);
    	return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    public static List<WebElement> waitForPresenceOfAllElementsLocatedBy(WebDriver driver, int timeOutSeconds, By locator) {
    	WebDriverWait wait = waitWebDriver(driver, timeOutSeconds);
    	return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }
    
    public static WebElement waitForPresenceOfElementLocated(WebDriver driver, int timeOutSeconds, By locator) {
    	WebDriverWait wait = waitWebDriver(driver, timeOutSeconds);
    	return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    /**
     * Wait until an element is no longer attached to the DOM.
     *
     * @param element The element to wait for.
     * @return false is the element is still attached to the DOM, true
     *         otherwise.
     */
    public static Boolean waitForStalenessOf(WebDriver driver, int timeOutSeconds, WebElement element) {
    	WebDriverWait wait = waitWebDriver(driver, timeOutSeconds);
    	return wait.until(ExpectedConditions.stalenessOf(element));
    }
    
    public static Boolean waitForTextToBePresentInElement(WebDriver driver, int timeOutSeconds, By locator, String text) {
    	WebDriverWait wait = waitWebDriver(driver, timeOutSeconds);
    	return wait.until(ExpectedConditions.textToBePresentInElement(locator, text));
    }
    
    public static Boolean waitForTextToBePresentInElementValue(WebDriver driver, int timeOutSeconds, By locator, String text) {
    	WebDriverWait wait = waitWebDriver(driver, timeOutSeconds);
    	return wait.until(ExpectedConditions.textToBePresentInElementValue(locator, text));
    }
    
    public static Boolean waitForTitleContains(WebDriver driver, int timeOutSeconds, String title) {
    	WebDriverWait wait = waitWebDriver(driver, timeOutSeconds);
    	return wait.until(ExpectedConditions.titleContains(title));
    }
    
    public static Boolean waitForTitleIs(WebDriver driver, int timeOutSeconds, String title) {
    	WebDriverWait wait = waitWebDriver(driver, timeOutSeconds);
    	return wait.until(ExpectedConditions.titleIs(title));
    }
    
    public static WebElement waitForVisibilityOf(WebDriver driver, int timeOutSeconds, WebElement element) {
    	WebDriverWait wait = waitWebDriver(driver, timeOutSeconds);
    	return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    public static WebElement waitForVisibilityOfElementLocated(WebDriver driver, int timeOutSeconds, By locator) {
    	WebDriverWait wait = waitWebDriver(driver, timeOutSeconds);
    	return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    private static WebDriverWait waitWebDriver(WebDriver driver, int timeOutSeconds) {
    	return new WebDriverWait(driver, timeOutSeconds);
    }
	
	public  static void explictWait(long timeOutSeconds,Function<? super WebDriver, Boolean> isTrue, WebDriver driver ) {
		 WebDriverWait wait = new WebDriverWait(driver, timeOutSeconds);
		 wait.until(isTrue);
		
	}
	
	public static void explictWait(long timeOutSeconds, Predicate<WebDriver> isTrue, WebDriver driver ) {
		 WebDriverWait wait = new WebDriverWait(driver, timeOutSeconds);
		 wait.until( isTrue);
	}
	
	public static void explictWait(long timeOutSeconds, ExpectedCondition<WebElement> isTrue, WebDriver driver ) {
		 WebDriverWait wait = new WebDriverWait(driver, timeOutSeconds);
		 wait.until( isTrue);
	}

	/**
	 * 显示的等待
	 * @param timeOutSeconds
	 * @param driver
     */
	public static void implicitWait(long timeOutSeconds, WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(timeOutSeconds, TimeUnit.SECONDS);
	}
	
   public static void sleep(Long msec) {
       try {
           Thread.sleep(msec);
       } catch (InterruptedException e) {
           e.printStackTrace(); // To change body of catch statement use File |
           // Settings | File Templates.
       }
   }

	/**
	 * 睡眠几秒钟
	 * @param seconds
     */
   public static void sleepSecond(int seconds) {
       sleep(1000L * seconds);
   }

	/**
	 * 最大化窗口
	 * @param driver
     */
	public static  void maximizeWindow(WebDriver driver){
		driver.manage().window().maximize();
	}

}
