package com.xiaohongshu.automation.service.webdriver.common;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.xiaohongshu.automation.service.webdriver.common.action.WebCheckBox;
import com.xiaohongshu.automation.service.webdriver.common.action.WebSelectElement;
import com.xiaohongshu.automation.service.webdriver.common.util.WebDriverHelper;
import com.xiaohongshu.automation.service.webdriver.common.util.WebDriverPropertiesReader;
import com.xiaohongshu.automation.utils.JSLog;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.slf4j.LoggerFactory;

/**
 * Add the javadoc to enable code review. The PageObject in PPMAutomation
 * project is a very important class which represent the D.D.D pattern. All the
 * logical PageObjects are inherited from this class. The PageObject provide the
 * basic functions to interact with webdriver browser page elements.
 *
 * @author tianchen
 */
public abstract class PageObject extends LoadableComponent<PageObject> {

    public enum IdentifyType {
        ID, XPATH, TAGNAME, LINKTEXT, NAME, CLASSNAME, PARTIALLINKTEXT, CSSSELECTOR
    }

    public WebDriver driver; // no default value for driver. updated
    public static int AJAX_WAIT_TIME = 6000;// default we wait 4s to complete the AJAX call.
    public String resourceFilePath;
    protected String host;
    protected String port;
    protected String contextURL; //访问URL地址
    private boolean isHttps = false;

    /**
     * IP + port
     * @param host
     * @param port
     */
    public PageObject(String host, String port) {
        setHost(host);
        setPort(port);
        getDriver();
    }

    /**
     * 域名访问
     * @param host
     */
    public PageObject(String host) {
        setHost(host);
        getDriver();
    }

    /**
     * 空
     */
    public PageObject() {
        setDriver(getDriver());
        contextURL = driver.getCurrentUrl();
    }

    public PageObject(WebDriver driver) {
        setDriver(driver);
    }


    public abstract String getResourceFilePath();
    public abstract String getContextURL();

    /**
     * Close browser and free up the driver.
     *
     * @author bpan@hp.com Date: 2012-11-08
     */
    public void closeBrowser() {
        try {
            this.driver.quit();
        } catch (Exception e) {
        }
        this.driver = null;
        if (DriverFactory.driverType.equals(DriverFactory.CHROME)) {
            //DriverFactory.closeChromeSerivce();
        }
    }

    /**
     * Get Web Element from the key defined in the resource file with the
     * default time out 10 seconds
     *
     * @param key The key value in the resource file
     * @return Web Element
     * @author zhao-kun.li@hp.com
     */
    public WebElement getElement(String key) {
        return getElement(getLocator(key));
    }

    /**
     * Get Web Element from the key defined in the resource file with the
     * specified time out In Seconds
     *
     * @param key              The key value in the resource file
     * @param timeOutInSeconds Time out in Seconds
     * @return Web Element
     * @author zhao-kun.li@hp.com
     */
    public WebElement getElement(String key, int timeOutInSeconds) {
        return getElement(getLocator(key), timeOutInSeconds);
    }

    /**
     * Get Web Element from the By locater with the specified time out In
     * Seconds
     *
     * @param by               By locater
     * @param timeOutInSeconds Time out in Seconds
     * @return Web Element
     * @author zhao-kun.li@hp.com
     */
    public WebElement getElement(By by, int timeOutInSeconds) {
        WebElement element = null;
        if (timeOutInSeconds == 0) {
            element = getElement(by);
        } else {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            element = getElement(by);
        }
        return element;
    }

    /**
     * Get Web Element from the By locater with the default time out In 10
     * Seconds
     *
     * @param by By locater
     * @return Web Element
     * @author zhao-kun.li@hp.com
     */
    public WebElement getElement(By by) {
        WebElement element = null;
        // implicit wait for 10 seconds
        element = driver.findElement(by);
        // Jeff Pan 2012-12-14:
        // Scroll this element into page.As following issue:
        // https://groups.google.com/forum/?fromgroups=#!topic/webdriver/R2jwSWrIK44
        // String js =
        // String.format("window.scroll(%s, %s)", element.getLocation().getX(),
        // element
        // .getLocation().getY());
        // ((JavascriptExecutor)driver).executeScript(js);
        // sleep((long)500);

        return element;
    }

    /**
     * According to requirement, add this function for Automation QA to just get
     * the Locator instead of the WebElement.
     *
     * @param key : The same input param as getElement(key) ;
     * @return By
     */
    public By getLocator(String key) {
        String elementIdentifier = searchIdentifier(key);
        By ret = null;
        String[] parseStrings = elementIdentifier.split("://");
        String identifyType = parseStrings[0].toUpperCase();
        String identifyValue = parseStrings[1];
        switch (IdentifyType.valueOf(identifyType)) {
            case ID:
                ret = By.id(identifyValue);
                break;
            case XPATH:
                ret = By.xpath(identifyValue);
                break;
            case TAGNAME:
                ret = By.tagName(identifyValue);
                break;
            case LINKTEXT:
                ret = By.linkText(identifyValue);
                break;
            case NAME:
                ret = By.name(identifyValue);
                break;
            case CLASSNAME:
                ret = By.className(identifyValue);
                break;
            case PARTIALLINKTEXT:
                ret = By.partialLinkText(identifyValue);
                break;
            case CSSSELECTOR:
                ret = By.cssSelector(identifyValue);
                break;
            default:
                break;
        }
        return ret;
    }

    /**
     * properties文件中, 每个page的元素, 请以 ID://开头
     * @param key
     * @return
     */
    private String searchIdentifier(String key) {
        WebDriverPropertiesReader propertiesReader = new WebDriverPropertiesReader(
                getResourceFilePath());
        String ret = propertiesReader.loadValue(key);
        if (null == ret) {
            ret = "ID://" + key;
        }
        return ret;
    }

    /**
     * Since we use the implicitWait policy. so we don't need to explicit wait
     * anymore.
     *
     * @return true
     */
    public boolean waitForAJAXCall() {
        boolean ret = true;

        // try {
        // Thread.sleep(AJAX_WAIT_TIME);
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        return ret;
    }

    private void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        try {
            if (null == driver || driver.getTitle() == null)
                driver = DriverFactory.getDriver();
        } catch (Exception e) {
            System.out.println("The browser  may have died.");
            driver = DriverFactory.getDriver();
        }
        return driver;
    }

    public void setContextURL(String contextURL) {
        this.contextURL = contextURL;
    }

    /**
     * 清空 并输入文字
     * @param field
     * @param text
     */
    public void clearAndType(WebElement field, String text) {
        field.clear();
        field.sendKeys(text);
    }

    public void clearAndType(WebElement field, String text, Keys key) {
        clearAndType(field, text);
        field.sendKeys(key);
    }

    public void typeValue(WebElement field, String text) {
        field.sendKeys(text);
    }

    public void typeValue(WebElement field, String text, Keys key) {
        typeValue(field, text);
        field.sendKeys(key);
    }

    public void typeValueAndTab(WebElement field, String text) {
        typeValue(field, text, Keys.TAB);
    }

    /**
     * 点击元素
     * @param field
     */
    public void click(WebElement field) {
        field.click();
    }

    public void tap(Point from, int xoffset, int yoffset) {
        Actions builder = new Actions(driver);

        builder.clickAndHold();

        Action build = builder.moveByOffset(xoffset, yoffset).release().build();
        build.perform();
        waitForAJAXCall();
    }

    public void tap(WebElement field, int xoffset, int yoffset) {
        Actions builder = new Actions(driver);
        if (null != field) {
            builder = builder.moveToElement(field, 0 - xoffset, 0 - yoffset);
        }
        Action build = builder.clickAndHold().moveByOffset(xoffset, yoffset)
                .release().build();
        build.perform();
        waitForAJAXCall();
    }

    /**
     * 提交按钮
     * @param field
     */
    public void submit(WebElement field) {
        field.submit();
    }

    public String selectDropDownListByText(String targetElementKey,
                                           String selectValue) {
        WebElement element = getElement(targetElementKey);
        return selectDropDwonListByText(element, selectValue);
    }

    public String selectDropDwonListByText(WebElement element,
                                           String selectValue) {
        String ret = "0";
        WebSelectElement select = new WebSelectElement(element);
        select.selectByText(selectValue);
        ret = select.getSelectedValue();
        return ret;
    }

    public void selectCheckBox(String targetElementKey) {
        WebElement element = getElement(targetElementKey);
        selectCheckBox(element);
    }

    public void selectCheckBox(WebElement element) {
        WebCheckBox checkBox = new WebCheckBox(element);
        checkBox.selectCheckBox();
    }

    public void unselectCheckBox(String targetElementKey) {
        WebElement element = getElement(targetElementKey);
        unselectCheckBox(element);
    }

    public void unselectCheckBox(WebElement element) {
        WebCheckBox checkBox = new WebCheckBox(element);
        checkBox.unSelectCheckBox();
    }


    public void explictWait(long timeOutSeconds,
                            Function<? super WebDriver, Boolean> isTrue) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutSeconds);
        wait.until(isTrue);
    }

    public void explictWait(long timeOutSeconds, Predicate<WebDriver> isTrue) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutSeconds);
        wait.until(isTrue);
    }

    public void explictWait(long timeOutSeconds,
                            ExpectedCondition<WebElement> isTrue) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutSeconds);
        wait.until(isTrue);
    }

    public void implicitWait(long timeOutSeconds) {
        driver.manage().timeouts()
                .implicitlyWait(timeOutSeconds, TimeUnit.SECONDS);
    }

    protected Alert waitForAlertIsPresent(int timeOutSeconds) {
        return WebDriverHelper.waitForAlertIsPresent(driver, timeOutSeconds);
    }

    protected boolean waitForElementSelectionStateToBe(int timeOutSeconds,
                                                       WebElement element, boolean selected) {
        return WebDriverHelper.waitForElementSelectionStateToBe(driver,
                timeOutSeconds, element, selected);
    }

    protected boolean waitForElementSelectionStateToBe(int timeOutSeconds,
                                                       By locator, boolean selected) {
        return WebDriverHelper.waitForElementSelectionStateToBe(driver,
                timeOutSeconds, locator, selected);
    }

    protected WebElement waitForElementToBeClickable(int timeOutSeconds,
                                                     By locator) {
        return WebDriverHelper.waitForElementToBeClickable(driver,
                timeOutSeconds, locator);
    }

    protected boolean waitForElementToBeSelected(int timeOutSeconds, By locator) {
        return WebDriverHelper.waitForElementToBeSelected(driver,
                timeOutSeconds, locator);
    }

    protected boolean waitForElementToBeSelected(int timeOutSeconds,
                                                 WebElement element) {
        return WebDriverHelper.waitForElementToBeSelected(driver,
                timeOutSeconds, element);
    }

    protected WebDriver waitForFrameToBeAvailableAndSwitchToIt(
            int timeOutSeconds, String frameLocator) {
        return WebDriverHelper.waitForFrameToBeAvailableAndSwitchToIt(driver,
                timeOutSeconds, frameLocator);
    }

    protected boolean waitForInvisibilityOfElementLocated(int timeOutSeconds,
                                                          By locator) {
        return WebDriverHelper.waitForInvisibilityOfElementLocated(driver,
                timeOutSeconds, locator);
    }

    protected List<WebElement> waitForPresenceOfAllElementsLocatedBy(
            int timeOutSeconds, By locator) {
        return WebDriverHelper.waitForPresenceOfAllElementsLocatedBy(driver,
                timeOutSeconds, locator);
    }

    protected WebElement waitForPresenceOfElementLocated(int timeOutSeconds,
                                                         By locator) {
        return WebDriverHelper.waitForPresenceOfElementLocated(driver,
                timeOutSeconds, locator);
    }

    /**
     * Wait until an element is no longer attached to the DOM.
     *
     * @param element The element to wait for.
     * @return false is the element is still attached to the DOM, true
     * otherwise.
     */
    protected Boolean waitForStalenessOf(int timeOutSeconds, WebElement element) {
        return WebDriverHelper.waitForStalenessOf(driver, timeOutSeconds,
                element);
    }

    protected Boolean waitForTextToBePresentInElement(int timeOutSeconds,
                                                      By locator, String text) {
        return WebDriverHelper.waitForTextToBePresentInElement(driver,
                timeOutSeconds, locator, text);
    }

    protected Boolean waitForTextToBePresentInElementValue(int timeOutSeconds,
                                                           By locator, String text) {
        return WebDriverHelper.waitForTextToBePresentInElementValue(driver,
                timeOutSeconds, locator, text);
    }

    protected Boolean waitForTitleContains(int timeOutSeconds, String title) {
        if (driver instanceof RemoteWebDriver) {
            WebDriverHelper.sleepSecond(timeOutSeconds);
            return true;
        }
        return WebDriverHelper.waitForTitleContains(driver, timeOutSeconds,
                title);
    }

    protected Boolean waitForTitleIs(int timeOutSeconds, String title) {
        return WebDriverHelper.waitForTitleIs(driver, timeOutSeconds, title);
    }

    protected WebElement waitForVisibilityOf(int timeOutSeconds,
                                             WebElement element) {
        return WebDriverHelper.waitForVisibilityOf(driver, timeOutSeconds,
                element);
    }

    protected WebElement waitForVisibilityOfElementLocated(int timeOutSeconds,
                                                           By locator) {
        return WebDriverHelper.waitForVisibilityOfElementLocated(driver,
                timeOutSeconds, locator);
    }


    /**
     * If there is only 1 window, switch to it; If there are 2 windows, switch
     * to the other window. FireFox's window name is like String
     * 'cc763827-bc24-42ab-a257-15dd2c898e74', can not use name in code
     * <p>
     * Usage:
     * <ol>
     * <li>...click to open new window
     * <li>switchToAnotherWindow();
     * <li>...operate on new window and close it
     * <li>switchToAnotherWindow();
     * <li>...continue operate on main window
     * </ol>
     * </p>
     */
    public void switchToAnotherWindow() {
        Set<String> winList = driver.getWindowHandles();
        // If only one window, swith to it
        if (winList.size() == 1) {
            driver.switchTo().window(winList.iterator().next());
            return;
        }
        if (winList.size() == 2) {
            String currWin = driver.getWindowHandle();
            for (String s : winList) {
                if (!currWin.equalsIgnoreCase(s)) {
                    driver.switchTo().window(s);
                    break;
                }
            }
            return;
        }
    }

    /**
     * Switch to the window with the containing title name and timeout second.
     * It has no limit on the number of the existing windows
     *
     * @param title         The title contained by the target window
     * @param timeOutSecond seconds to wait for finding the window
     * @author lizhaok Nov/29/2012
     */
    public void switchToWindow(String title, int timeOutSecond) {
        int count = timeOutSecond;
        boolean isFound = false;
        while (count > 0) {
            Set<String> winList = driver.getWindowHandles();
            for (String s : winList) {
                if (s.trim().length() == 0) {
                    continue;
                }
                try {
                    driver.switchTo().window(s);
                    if (driver.getTitle().contains(title)) {
                        isFound = true;
                        break;
                    }
                } catch (StaleElementReferenceException e) {
                    // TODO: handle exception
                } catch (NoSuchWindowException e) {
                    // TODO: handle exception
                }
            }
            if (isFound) {
                break;
            }
            WebDriverHelper.sleepSecond(1);
        }
        if (!isFound) {
            JSLog.error("The window with the brower titile: " + title
                    + " has not found after " + timeOutSecond + ".");
        }
    }

    public void closeSecondWindow() {
        Set<String> winList = driver.getWindowHandles();
        // If only one window, swith to it
        if (winList.size() == 2) {
            String currWin = driver.getWindowHandle();
            for (String s : winList) {
                if (!currWin.equalsIgnoreCase(s)) {
                    driver.switchTo().window(s);
                    JQDriver.getInstance(driver).jqueryJS("closeWindoid()");
                    driver.switchTo().window(currWin);
                    WebDriverHelper.sleepSecond(3);
                    break;
                }
            }
            return;
        }
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    public boolean verifyElementExistByXpath(String xpath, int timeOutSecond) {
        boolean isExist = false;
        try {
            if (getElement(By.xpath(xpath), timeOutSecond).isDisplayed()) {
                JSLog.info("WebElement exist with xpath: " + xpath);
                isExist = true;
            } else {
                isExist = false;
                JSLog.info("WebElement does not exist with xpath: " + xpath);
            }
        } catch (TimeoutException e) {
            isExist = false;
            JSLog.info("Time out to find the xpath " + xpath + "Error: "
                    + e.getMessage());
        } catch (NoSuchElementException e) {
            isExist = false;
            JSLog.info("WebElement does not exist with xpath: " + xpath
                    + "Error: " + e.getMessage());
        }
        return isExist;
    }

    public void promptAlert() {
        // In some case , there is a ALERT prompt...
        try {
            Alert promptIf = driver.switchTo().alert();
            if (promptIf != null) {
                promptIf.accept();
            }
        } catch (NoAlertPresentException exception) {
            driver.switchTo().defaultContent();
        }
        driver.switchTo().defaultContent();
    }

    // During loading the pageobject, isloaded() method runs first and if it is
    // not the expected url, it will call the load() method;
    // At last it will call isLoaded() method again to verify whether it is
    // loaded successfully or not
    @Override
    protected void isLoaded() throws Error {
        String url = null;
        try {
            url = getDriver().getCurrentUrl();

        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
        if (null != url && url.endsWith(getContextURL())) {
            // Assert.assertTrue("Not on the load page: " + url,
            // url.endsWith(getContextURL()));
            LoggerFactory.getLogger(PageObject.class).info("isLoaded=true");
            LoggerFactory.getLogger(PageObject.class).info("current url=" + url);
            LoggerFactory.getLogger(PageObject.class).info("provide url=" + getContextURL());
        } else {
            LoggerFactory.getLogger(PageObject.class).info("isLoaded=false");
            LoggerFactory.getLogger(PageObject.class).info("current url=" + url);
            LoggerFactory.getLogger(PageObject.class).info("provide url=" + getContextURL());
            throw new Error("Not loaded yet!");
        }
    }

    /**
     * 配置是否为https请求
     * @param isHttps
     * @return
     */
    public boolean setHttps(boolean isHttps) {
        this.isHttps = isHttps;
        return this.isHttps;
    }

    /**
     * 拼装访问URL
     * @return
     */
    protected String getURL() {
        if (host != null && port != null) {
            if (isHttps == false) {
                return "http://" + host + ":" + port + getContextURL();
            } else {
                return "https://" + host + ":" + port + getContextURL();
            }
        } else if (host != null && port == null) {
            if (isHttps == false) {
                return "http://" + host  + getContextURL();
            } else {
                return "https://" + host + getContextURL();
            }
        }else {
            return null;
        }
    }

    /**
     * 开始加载页面的方法
     */
    @Override
    protected void load() {
        if (host != null) {
            String url = getURL();

            Logger.getLogger(PageObject.class.getName()).info("Set the page load timeout for 30 seconds");

            if (!(driver instanceof RemoteWebDriver)) {
                driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            } else {
                WebDriverHelper.implicitWait(30, driver);
            }
            Logger.getLogger(PageObject.class.getName()).info("Will load the PageObject... the next line is getDriver().get(url) and URL is [" + url + "]");
            getDriver().get(url);

            // Description: Try 10 times to reload page if loading page failed
            int timeOutTimes = 1;
            while (timeOutTimes <= 10) {
                String currentURL = null;
                try {
                    currentURL = getDriver().getCurrentUrl();

                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                }
                if (!currentURL.endsWith(getContextURL())) {
                    Logger.getLogger(PageObject.class.getName()).info("Try reload the page in the: " + timeOutTimes + " times with the URL: " + url);
                    getDriver().get(url);
                    timeOutTimes++;
                    WebDriverHelper.sleepSecond(timeOutTimes);
                } else {
                    break;
                }
            }
            Logger.getLogger(PageObject.class.getName()).info("Will init the elements... with driver:[" + driver + "]...");
            LoggerFactory.getLogger(PageObject.class).info("host=" + host);
            PageFactory.initElements(driver, this);

        } else {
            LoggerFactory.getLogger(PageObject.class).info("Can't find the host");
        }
    }

    /**
     * 从配置文件读取参数
     * @param key
     * @return
     */
    public String getPropertiy(String key) {
        WebDriverPropertiesReader propertiesReader = new WebDriverPropertiesReader(
                getResourceFilePath());
        return propertiesReader.loadValue(key);
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
