package com.xiaohongshu.automation.service.db;

/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LongField extends Field
{

    long value;
    /**
     * @see com.db.Field#getValue()
     */
    public Object getValue()
    {
        return new Long(value);
    }
    public void setValue(long aValue)
    {
        value = aValue;
    }
    public LongField()
    {
        type = TYPE_LONG;
    }
}
