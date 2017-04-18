package com.xiaohongshu.automation.service.db;


/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class StringField extends Field {
	String value;
	public Object getValue() {
		return value;
	}
	public void setValue(String aValue) {
		value = aValue;
	}
	public StringField() {
		type = TYPE_STRING;
	}
    public String getStringValue()  {
        return value ;
    }
}
