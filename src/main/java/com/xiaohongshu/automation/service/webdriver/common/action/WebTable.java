package com.xiaohongshu.automation.service.webdriver.common.action;

import java.util.List;

import com.xiaohongshu.automation.utils.JSLog;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author tianchen
 */
public class WebTable {
    private WebElement table;
    private List<WebElement> rowList;
    private List<WebElement>	 columnList;
    private WebElement selectedRow;
    private WebElement selectedColumn;
    
    public WebTable(WebDriver driver) {
    	table = driver.findElement(By.id("datatable"));
    }
    
    public WebTable(WebElement tableElement) {
        table = tableElement;
    }
    
    public WebElement selectRowByIndex(int index) {
    	rowList = table.findElements(By.tagName("tr"));  
    	selectedRow = rowList.get(index);
    	return selectedRow;
    }
	
    public List<WebElement> selectAllRows() {
    	return table.findElements(By.tagName("tr"));  
    }
    
    public WebElement seleceColumnByIndex(int index, WebElement targetRow) {
    	columnList =  targetRow.findElements(By.tagName("td"));
    	selectedColumn = columnList.get(index);
    	return selectedColumn;
    }
    
    public WebElement selectCell(int row, int column) {
    	selectedRow =selectRowByIndex(row);
    	selectedColumn = seleceColumnByIndex(column,selectedRow);
    	return selectedColumn;
    }
    
    public WebElement selectRowByName(String name) {
    	boolean selected = false;
    	rowList = table.findElements(By.tagName("tr"));  
    	for(WebElement element : rowList) {
    		JSLog.info("=" + element.getText() + "=");
    		if (element.getText().equals(name)) {
    			selectedRow = element;
    			selectedRow.click();
				selected = true;
				break;
			}
    	}
    	if (!selected)
			throw new NoSuchElementException(
					"The 'select' does not contains the item with text='"
							+ name + "'");
    	return selectedRow;
    }
    
    public WebElement selectColumnByName(String name, WebElement targetRow) {
    	boolean selected = false;
    	columnList =  targetRow.findElements(By.tagName("td"));
    	for(WebElement element : columnList) {
    		if (element.getText().equals(name)) {
    			selectedColumn = element;
    			selectedColumn.click();
				selected = true;
				break;
			}
    	}
    	if (!selected)
			throw new NoSuchElementException(
					"The 'select' does not contains the item with text='"
							+ name + "'");
    	return selectedColumn;
    }
    
    public WebElement selectCellByName(String name) {
    	boolean selected = false;
    	rowList = table.findElements(By.tagName("tr")); 
    	for(WebElement rowElement : rowList) {
    		columnList =  rowElement.findElements(By.tagName("td"));
    		for(WebElement element : columnList) {
        		if (element.getText().equals(name)) {
        			selectedColumn = element;
        			selectedColumn.click();
    				selected = true;
    				break;
    			 }
        		
    	      }
    		if(selected)
    			break;
    	}
    	if (!selected)
			throw new NoSuchElementException(
					"The 'select' does not contains the item with text='"
							+ name + "'");
    	return selectedColumn;
    }
    
    public int getRowByName(String name){
    	rowList = table.findElements(By.tagName("tr"));    	
    	for(WebElement element : rowList) {
    		if (element.getText().contains(name)) {
    			selectedRow = element;
				break;
			}
       }
     return	rowList.indexOf(selectedRow);
    }
    
    
    
	public WebElement getTable() {
        return table;
    }

    public List<WebElement> getRowList() {
        return rowList;
    }

    public List<WebElement> getColumnList() {
        return columnList;
    }

    public WebElement getSelectedRow() {
        return selectedRow;
    }

    public WebElement getSelectedColumn() {
        return selectedColumn;
    }

    /**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

}
