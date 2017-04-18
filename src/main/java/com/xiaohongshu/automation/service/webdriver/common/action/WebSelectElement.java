package com.xiaohongshu.automation.service.webdriver.common.action;

import java.util.ArrayList;
import java.util.List;

import com.xiaohongshu.automation.utils.JSLog;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import org.slf4j.LoggerFactory;

public class WebSelectElement extends RemoteWebElement implements WebElement {

	/**
	 * <select></select>}
	 */
	private WebElement selectTag;

	private List<WebElement> options = new ArrayList<WebElement>();

	public WebSelectElement(WebElement element) {
		selectTag = element;
		refreshOptions();
	}

	private void refreshOptions() {
		options = selectTag.findElements(By.tagName("option"));
	}

	public void selectByText(String text) {
		selectText(true, text);
	}

	public void selectByFuzzyText(String text) {
		selectText(false, text);
	}

	public void selectText(boolean isEqual, String text)
			throws NoSuchElementException {
		refreshOptions();
		if (null != options) {
			boolean selected = false;
			for (WebElement option : options) {
				boolean isExist = false;
				LoggerFactory.getLogger(WebSelectElement.class).info("Option=[" + option.getText() + "]");
				if (isEqual) {
					isExist = option.getText().equals(text);
				} else {
					isExist = option.getText().contains(text);
				}
				if (isExist) {
					option.click();
					selected = true;
					break;
				}
			}
			if (!selected)
				throw new NoSuchElementException(
						"The 'select' does not contains the item with text='"
								+ text + "'");
		} else {
			throw new NoSuchElementException(
					"The 'select' options list is empty");
		}
	}
	public String getSelectedText() throws NoSuchElementException {
		String ret = "";
		refreshOptions();
		if (null != options) {
			boolean selected = false;
			for (WebElement option : options) {
				if (option.isSelected()) {
					return option.getText();
				}
			}
		} else {
			throw new NoSuchElementException(
					"The 'select' options list is empty");
		}
		return ret;
	}

	public String getSelectedValue() throws NoSuchElementException {
		String ret = "";
		refreshOptions();
		if (null != options) {
			boolean selected = false;
			for (WebElement option : options) {
				if (option.isSelected()) {
					return option.getAttribute("value");
				}
			}
		} else {
			throw new NoSuchElementException(
					"The 'select' options list is empty");
		}
		return ret;
	}

	public boolean verifyTextExist(String text) {
		boolean isExist = false;
		for (WebElement option : options) {
			if (option.getText().equals(text)) {
				JSLog.info(text + " exists in " + selectTag.toString());
				isExist = true;
				break;
			}
		}
		if (!isExist) {
			JSLog.info(text + "does not exists in " + selectTag.toString());
		}
		return isExist;
	}

}
