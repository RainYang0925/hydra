package com.xiaohongshu.automation.service.db;

import java.util.Date;

/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DateField extends Field {
	Date value;
	/**
	 * @see com.db.Field#getValue()
	 */
	public Object getValue() {
		return value;
	}
	public void setValue(Date aValue) {
		value = aValue;
	}
    
    public Date getDateValue() {
        return value ;
    }
    
    @Override
    public String getType() {
    	// TODO Auto-generated method stub
    	return TYPE_DATE;
    }
}
