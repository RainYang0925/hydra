package com.xiaohongshu.automation.service.db;

/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FloatField extends Field {
	float value;
	/**
	 * @see com.db.Field#getValue()
	 */
	public Object getValue() {
		return new Float(value);
	}
	/**
	 * 
	 * @param aValue
	 */
	public void setValue(float aValue) {
		value = aValue;
	}
	/**
	 * 
	 * @see Object#Object()
	 */
	public FloatField() {
		type = TYPE_FLOAT;
	}
    
    public float getFloatValue() {
        return value ;
    }
}
