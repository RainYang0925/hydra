package com.xiaohongshu.automation.service.webdriver.common.action;

import java.util.List;

import com.xiaohongshu.automation.service.webdriver.common.util.WebDriverHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Handle the actions in the AutoComeple Dialog. There are different types of autocomplete dialog in the ppm with ok button; with ok and find button; 
 * without ok button but with find button; without both of ok and find button
 * 
 */
public class AutoCompleteDialog  {
	/**
	 * Get all of the search results from the autocompelete dialog with ok and find button
	 * @param searchElement  The targeting searching editbox in the autocomplete dialog, NOT the editbox next to the autocomplete button
	 * @param searchValue  The value to search in the searching editbox
	 * @return driver   The current webdriver to attach with
	 */
	 public void getAllResultWithOkButton(WebElement searchElement, String searchValue, WebDriver driver) {
		 getResultWithOkButton(searchElement, searchValue, true, driver);
	}
	 
	 public void getAllResultWithOkButton(By searchElement, String searchValue, WebDriver driver) {
		 getResultWithOkButton(searchElement, searchValue, true, driver);
	}

	 /**
		 * Get single  of the search results from the autocompelete dialog with ok and without find button
		 * @param searchElement  The targeting searching editbox in the autocomplete dialog, NOT the editbox next to the autocomplete button
		 * @param searchValue  The value to search in the searching editbox
		 * @return driver   The current webdriver to attach with
		 */

	 /**
		 * Get the first search result from the autocompelete dialog with ok and without find button
		 * @param searchElement  The targeting searching editbox in the autocomplete dialog, NOT the editbox next to the autocomplete button
		 * @param searchValue  The value to search in the searching editbox
		 * @return driver   The current webdriver to attach with
		 */	 
	 public void getExactResultWithOkWithoutFindButton(WebElement searchElement, String searchValue,  WebDriver driver){
		 commonSearchInAC(searchElement, searchValue, driver, false);
		 searchInTableWithOKWithoutFind(searchValue,driver);
	 }
	 
	 public void getExactResultWithOkWithoutFindButton(By searchElement, String searchValue,WebDriver driver){
		 commonSearchInAC(searchElement, searchValue, driver, false);
		 searchInTableWithOKWithoutFind(searchValue, driver);
	 }
	
	private void searchInTableWithOKWithoutFind(String searchValue, WebDriver driver) {
			WebElement table = driver.findElement(By.id("datatable"));
			//Find rows
			List<WebElement> rows = table.findElements(By.tagName("tr"));  
			for(int index = 1; index < rows.size(); index ++)
			{
				WebDriverHelper.sleep((long) 500);
				WebElement subElement = rows.get(index).findElement(By.tagName("th"));
				if (subElement.getText().trim().equals(searchValue)) {
					subElement.click();
					WebDriverHelper.sleep((long) 500);
					break;
				}
			}
			//Note: switch back to the father content and then find the child frame otherwise it will not find it
			driver.switchTo().defaultContent();
			driver.switchTo().frame("autoCompleteDialogIF");
			WebDriverHelper.sleepSecond(1);
			driver.findElement(By.id("btnOK")).click();
			WebDriverHelper.sleepSecond(1);
			driver.switchTo().defaultContent();
		}
		


	/**
	 * Get the first search result from the autocompelete dialog with ok and find button
	 * @param searchElement  The targeting searching editbox in the autocomplete dialog, NOT the editbox next to the autocomplete button
	 * @param searchValue  The value to search in the searching editbox
	 * @return driver   The current webdriver to attach with
	 */
	public void getSingleResultWithOkButton(WebElement searchElement, String searchValue, WebDriver driver) {
		getResultWithOkButton(searchElement, searchValue, false, driver);
	}
	
	public void getSingleResultWithOkButton(By searchElement, String searchValue, WebDriver driver) {
		getResultWithOkButton(searchElement, searchValue, false, driver);
	}
	
	private void getResultWithOkButton(WebElement searchElement, String searchValue, boolean isAll, WebDriver driver) {	
		commonSearchInAC(searchElement, searchValue, driver, true);
		searchInTable(isAll, driver);
	}
	
	private void getResultWithOkButton(By searchElement, String searchValue, boolean isAll, WebDriver driver) {	
		commonSearchInAC(searchElement, searchValue, driver, true);
		searchInTable(isAll, driver);
	}

	private void searchInTable(boolean isAll, WebDriver driver) {
		WebElement table = driver.findElement(By.id("datatable"));
		//Find rows
		List<WebElement> rows = table.findElements(By.tagName("tr"));  
		if(!isAll) {
			//Starting from 1
			WebElement theRow = rows.get(1);
			WebDriverHelper.sleepSecond(2);
			theRow.click();
			WebDriverHelper.sleepSecond(2);
		} 
		else {		
			//Starting from 1
			for(int index = 1; index < rows.size(); index ++)
			{
				WebElement theRow = rows.get(index);
				WebDriverHelper.sleepSecond(2);
				theRow.click();
				WebDriverHelper.sleepSecond(2);
			}		
		}
		//Note: switch back to the father content and then find the child frame otherwise it will not find it
		driver.switchTo().defaultContent();
		driver.switchTo().frame("autoCompleteDialogIF");
		WebDriverHelper.sleepSecond(1);
		driver.findElement(By.id("btnOK")).click();
		WebDriverHelper.sleepSecond(1);
		driver.switchTo().defaultContent();
	}
	
	/**
	 * Get the first single search result from the autocompelete dialog without ok button but with find button
	 * @param searchElement  The targeting searching editbox in the autocomplete dialog, NOT the editbox next to the autocomplete button
	 * @param searchValue  The value to search in the searching editbox
	 * @return driver   The current webdriver to attach with
	 */
	public void getSingleResultWithoutOkButton(WebElement searchElement, String searchValue, WebDriver driver) {
		getResultWithoutOkButton(searchElement, searchValue, false, driver);
	}
	
	public void getSingleResultWithoutOkButton(By searchElement, String searchValue, WebDriver driver) {
		getResultWithoutOkButton(searchElement, searchValue, false, driver);
	}
	
	/**
	 * Get the first single search result from the autocompelete dialog without ok button and find button
	 * @param searchElement  The targeting searching editbox in the autocomplete dialog, NOT the editbox next to the autocomplete button
	 * @param searchValue  The value to search in the searching editbox
	 * @return driver   The current webdriver to attach with
	 */
	public void getSingleResultWithoutOkAndFindButton(WebElement searchElement, String searchValue, WebDriver driver) {
		commonSearchInAC(searchElement, searchValue, driver, false);
		WebElement table = driver.findElement(By.id("datatable"));
	    WebElement theRow = table.findElement(By.xpath("//tr[contains(@id, " + "'"+ searchValue + "')]"));	
	    theRow.click();
		
		//Note: switch back to the father content and then find the child frame otherwise it will not find it
		driver.switchTo().defaultContent();
	}
	
	public void getSingleResultWithoutOkAndFindButton(By searchElement, String searchValue, WebDriver driver) {
		commonSearchInAC(searchElement, searchValue, driver, false);
		WebElement table = driver.findElement(By.id("datatable"));
	    WebElement theRow = table.findElement(By.xpath("//tr[contains(@id, " + "'"+ searchValue + "')]"));	
	    theRow.click();
		
		//Note: switch back to the father content and then find the child frame otherwise it will not find it
		driver.switchTo().defaultContent();
	}
	
	private void getResultWithoutOkButton(WebElement searchElement, String searchValue, boolean isAll, WebDriver driver)
	{
		commonSearchInAC(searchElement, searchValue, driver, true);
		searchInTable(driver);
	}
	
	private void getResultWithoutOkButton(By searchElement, String searchValue, boolean isAll, WebDriver driver)
	{
		commonSearchInAC(searchElement, searchValue, driver, true);
		searchInTable(driver);
	}
	
	private void searchInTable(WebDriver driver) {
		WebElement table = driver.findElement(By.id("datatable"));
		// find no result
		if (table == null) {
			driver.findElement(By.id("btnOK")).click();
			WebDriverHelper.sleepSecond(1);
			driver.switchTo().defaultContent();
			return;
		}
		//Find rows
		List<WebElement> rows = table.findElements(By.tagName("tr"));  
	
		//Starting from 2 in simple  find dialog without ok button
		WebElement theRow = rows.get(2);
		WebDriverHelper.sleepSecond(3);
		theRow.click();

        WebDriverHelper.sleepSecond(6);
		//No multiply selections function in simple find dialog without ok button 
		
		//Note: switch back to the father content and then find the child frame otherwise it will not find it
		driver.switchTo().defaultContent();
	}
	
	/**
	 * Get the exact the same result  from the autocompelete dialog with ok button and find button
	 * @param searchElement  The targeting searching editbox in the autocomplete dialog, NOT the editbox next to the autocomplete button
	 * @param searchValue  The value to search in the searching editbox
	 * @return driver   The current webdriver to attach with
	 */
	public void getExactResultWithOkButton(WebElement searchElement, String searchValue, WebDriver driver){	
		commonSearchInAC(searchElement, searchValue, driver, true);
		searchInWithOkButtonTable(driver, searchValue);
	}
	
	/**
	 * Get the exact the same result  from the autocompelete dialog with ok button and find button
	 * @param searchValue  The value to search in the searching editbox
	 * @return driver   The current webdriver to attach with
	 */
	public void getExactResultWithOkButton(By searchBy, String searchValue, WebDriver driver){	
		commonSearchInAC(searchBy, searchValue, driver, true);
		searchInWithOkButtonTable(driver, searchValue);
	}
	
	private void searchInWithOkButtonTable( WebDriver driver, String searchValue) {
		WebElement table = driver.findElement(By.id("datatable"));
		//Find rows
		List<WebElement> rows = table.findElements(By.tagName("tr"));  
		for(int index = 1; index < rows.size(); index ++)
		{
			WebDriverHelper.sleep((long) 500);
			List<WebElement> columns = rows.get(index).findElements(By.tagName("td"));
			WebElement targetColumn = columns.get(0);
			// !!Dugging TECH to avoid space: System.out.println("["+targetColumn.getText()+"]");
			if (targetColumn.getText().trim().equals(searchValue)) {
				targetColumn.click();
				WebDriverHelper.sleep((long) 500);
				break;
			}
		}
		//Note: switch back to the father content and then find the child frame otherwise it will not find it
		driver.switchTo().defaultContent();
		driver.switchTo().frame("autoCompleteDialogIF");
		WebDriverHelper.sleepSecond(1);
		driver.findElement(By.id("btnOK")).click();
		WebDriverHelper.sleepSecond(1);
		driver.switchTo().defaultContent();
	}
	
	private void commonSearchInAC(WebElement searchElement, String searchValue, WebDriver driver, boolean clickFindButton) {
		//Wait for the auto complete dialog shows up
		WebDriverHelper.sleepSecond(5);
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("autoCompleteDialogIF"));
		WebDriverHelper.sleepSecond(1);
		searchElement.sendKeys(searchValue);
		WebDriverHelper.sleepSecond(2);
		if(clickFindButton) {
		    driver.findElement(By.id("filterPanelFindButton")).click();
		}
		WebDriverHelper.sleepSecond(2);
		//Switch to the sub frame which holds the datatable
		driver.switchTo().frame("availFrame");
		WebDriverHelper.sleepSecond(1);
	}
	
	private void commonSearchInAC(By searchElement, String searchValue, WebDriver driver, boolean clickFindButton) {
		//Wait for the auto complete dialog shows up
		WebDriverHelper.sleepSecond(5);
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("autoCompleteDialogIF"));
		WebDriverHelper.sleepSecond(1);
		driver.findElement(searchElement).sendKeys(searchValue);
		//searchElement.sendKeys(searchValue);
		WebDriverHelper.sleepSecond(2);
		if(clickFindButton) {
		    driver.findElement(By.id("filterPanelFindButton")).click();
		}
		WebDriverHelper.sleepSecond(2);
		//Switch to the sub frame which holds the datatable
		driver.switchTo().frame("availFrame");
		WebDriverHelper.sleepSecond(1);
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
