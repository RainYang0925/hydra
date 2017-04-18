package com.xiaohongshu.automation.service.db;

/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class Field {
	public static final String TYPE_STRING="string";
	public static final String TYPE_FLOAT="float";
	public static final String TYPE_INT="int";
	public static final String TYPE_LONG="long";
	public static final String TYPE_DOUBLE="double";
	public static final String TYPE_DATE="date";

	String name;
	String type;
    String description;
    boolean isNeed = false ;
    boolean isPrimaryKey = false ;
	public abstract Object getValue();
	
	public void setName(String aname) {
		name = aname;
	}
	public void setType(String atype) {
		type = atype;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
    /**
     * @return Returns the description.
     */
    protected String getDescription() {
        return description;
    }
    /**
     * @param description The description to set.
     */
    protected void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return Returns the isNeed.
     */
    protected boolean isNeed() {
        return isNeed;
    }
    /**
     * @param isNeed The isNeed to set.
     */
    protected void setNeed(boolean isNeed) {
        this.isNeed = isNeed;
    }
    
    public static Field createField(String type, String name) {
    	Field ret = null ;
    	if (TYPE_STRING.equals(type)){
    		ret = new StringField();    		
    	} else if (TYPE_INT.equals(type)){
    		ret = new IntField();
    	} else if (TYPE_LONG.equals(type)){
    		ret = new LongField();
    	} else if (TYPE_FLOAT.equals(type)){
    		ret = new FloatField() ;
    	} else if (TYPE_DOUBLE.equals(type)){
    		ret = new DoubleField();
    	} else if (TYPE_DATE.equals(type)){
    		ret = new DateField();
    	}
    	if (null!=ret) ret.setName(name);
    	return ret ;
    }
}
