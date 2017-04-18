package com.xiaohongshu.automation.service.db;

/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class IntField extends Field {
	int value;
	public Object getValue() {
		return new Integer(value);
	}
	public void setValue(int aValue) {
		value = aValue;
	}
	public IntField() {
		type = TYPE_INT;
	}
    public int getIntValue() {
        return value ;
    }
}
