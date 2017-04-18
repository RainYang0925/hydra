package com.xiaohongshu.automation.service.db;

/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DoubleField extends Field {
	double value;
	/**
	 * @see com.db.Field#getValue()
	 */
	public Object getValue() {
		return new Double(value);
	}
	public void setValue(double aValue) {
		value = aValue;
	}
    public double getDoubleValue() {
        return value ;
    }
    
    @Override
    public String getType() {
    	// TODO Auto-generated method stub
    	return TYPE_DOUBLE;
    }
}
