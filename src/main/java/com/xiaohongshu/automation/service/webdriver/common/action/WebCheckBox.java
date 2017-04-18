package com.xiaohongshu.automation.service.webdriver.common.action;

import com.xiaohongshu.automation.utils.JSLog;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;


/*
 * For CheckBox or Radio button on web page
 */
public class WebCheckBox {
    private WebElement checkBox;
    
    public WebCheckBox(WebElement checkBox) {
    	this.checkBox = checkBox;
    }
	
	public void selectCheckBox() {
		if(!checkBox.isSelected()) {
			checkBox.click();
			if(checkBox.isSelected()) {
				JSLog.info(checkBox + "has been selected successfully");
			}
			else {
				LoggerFactory.getLogger(WebCheckBox.class).error(checkBox + "has been selected failed");
			}
			
		}
		else {
			JSLog.info(checkBox + "has already been selected");
		}
	}
	
	public void unSelectCheckBox() {
		if(checkBox.isSelected()) {
			checkBox.click();
			if(!checkBox.isSelected()) {
				JSLog.info(checkBox + "has been unselected successfully");
			}
			else {
				JSLog.error(checkBox + "has been unselected failed");
			}
			
		}
		else {
			JSLog.info(checkBox + "has already been unselected");
		}
	}
	
	public boolean verifySelectedStatus(boolean isSelected) {
		if(isSelected){
			if(checkBox.isSelected()) {
				JSLog.info("Checked pass");
				return true;
			}else {
				JSLog.error("Checked error");
				return false;
			}
		}else {
			if(checkBox.isSelected()) {
				JSLog.error("UnChecked error");
				return false;
			}else {
				JSLog.info("UnChecked pass");
				return true;
			}
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
