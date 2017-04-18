package com.xiaohongshu.automation.service.webdriver.common;

import org.openqa.selenium.WebDriver;

public class ThreadBasedDriver {
	static final ThreadLocal<WebDriver> driverMap = new ThreadLocal();

	public WebDriver get() {
		return driverMap.get();
	}

	public void set(WebDriver value) {
		driverMap.set(value);
	}

	public void remove() {
		driverMap.remove();
	}
	
}
