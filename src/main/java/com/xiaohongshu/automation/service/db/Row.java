
package com.xiaohongshu.automation.service.db;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.sql.*;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Row {
    private static final int BUFFLENGTH = 1028;

    private static String[] date_pattern = new String[] {"yyyy-MM", "yyyyMM", "yyyy/MM", "yyyyMMdd", "yyyy-MM-dd",
            "yyyy/MM/dd", "yyyy/MM/dd", "MM/dd/yyyy", "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss"};

    Field[] fields = null;

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    public void trace() {
        LoggerFactory.getLogger(Row.class).info("--->>>>-->>BEGIN TRACE ROW--->>-->>--->>");
        if (fields != null) {
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                LoggerFactory.getLogger(Row.class).info("Field(" + field.getName() + ")=" + field.getValue() + "|");
            }
            LoggerFactory.getLogger(Row.class).info("");
        } else {
            LoggerFactory.getLogger(Row.class).info(" NODATA! END");
        }
    }

    public long getLong(String fieldName) {
        Field field = getField(fieldName);
        long ret = 0;
        if (field != null) {
            if ("double".equalsIgnoreCase(field.getType())) {
                ret = ((Double)field.getValue()).longValue();
            } else if ("FLOAT".equalsIgnoreCase(field.getType())) {
                ret = ((Float)field.getValue()).longValue();
            } else if ("INT".equalsIgnoreCase(field.getType())) {
                ret = ((Integer)field.getValue()).longValue();
            } else {
                ret = Long.parseLong(String.valueOf(field.getValue()));
            }
        }
        return ret;
    }

    public int getInt(String fieldName) {
        Field field = getField(fieldName);
        // Logger.trace(field.getName()+" type='"+field.getType()+"
        // value='"+field.getValue().toString()) ;
        int ret = 0;
        if (field != null) {
            if ("double".equalsIgnoreCase(field.getType())) {
                ret = ((Double)field.getValue()).intValue();
            } else if ("FLOAT".equalsIgnoreCase(field.getType())) {
                ret = ((Float)field.getValue()).intValue();
            } else if ("INT".equalsIgnoreCase(field.getType())) {
                ret = ((Integer)field.getValue()).intValue();
            } else {
                ret = Integer.parseInt(String.valueOf(field.getValue()));
            }
        }
        return ret;
    }

    public float getFloat(String fieldName) {
        Field field = getField(fieldName);
        float ret = 0;
        if (field != null) {
            ret = Float.parseFloat(String.valueOf(field.getValue()));
        }
        return ret;
    }

    public double getDouble(String fieldName) {
        Field field = getField(fieldName);
        double ret = 0;
        if (field != null) {
            ret = Double.parseDouble(String.valueOf(field.getValue()));
        }
        return ret;
    }

    public Date getDate(String fieldName) {
        Field field = getField(fieldName);
        Date ret = null;
        if (field != null) {
            if (field instanceof DateField) {
                return ((DateField)field).getDateValue();
            } else {
                try {
                    ret = DateUtils.parseDate(field.getValue().toString(), date_pattern);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        return ret;
    }

    public String getString(String fieldName) {
        Field field = getField(fieldName);
        String ret = "";
        if (field != null) {
            ret = String.valueOf(field.getValue());
        }
        return ret;
    }

    /**
     * @param pst
     * @throws SQLException
     */
    public void bind2Pst(PreparedStatement pst) throws SQLException {
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field instanceof IntField) {
                pst.setInt(i, ((IntField)field).getIntValue());
            } else if (field instanceof StringField) {
                pst.setString(i, ((StringField)field).getStringValue());
            } else if (field instanceof LongField) {
                pst.setLong(i, ((LongField)field).value);
            } else if (field instanceof FloatField) {
                pst.setFloat(i, ((FloatField)field).getFloatValue());
            } else if (field instanceof DateField) {
                pst.setTimestamp(i, new Timestamp(((DateField)field).getDateValue().getTime()));
            } else if (field instanceof DoubleField) {
                pst.setDouble(i, ((DoubleField)field).getDoubleValue());
            }
        }
    }

    /**
     * @param rs
     */
    public void importRsNoValue(ResultSet rs) {
        ResultSetMetaData columns;
        try {
            columns = rs.getMetaData();
            fields = new Field[columns.getColumnCount()];
            for (int i = 1; i <= columns.getColumnCount(); i++) {
                String name = columns.getColumnName(i);
                Field field = null;
                switch (columns.getColumnType(i)) {
                    case Types.DATE:
                        field = new DateField();
                        break;
                    case Types.INTEGER:
                        field = new IntField();
                        break;
                    case Types.BIGINT:
                        field = new LongField();
                        break;
                    case Types.FLOAT:
                        field = new FloatField();
                        break;
                    case Types.DOUBLE:
                        field = new DoubleField();
                        break;
                    case Types.DECIMAL:
                        if (columns.getScale(i) > 0) {
                            field = new DoubleField();
                        } else {
                            field = new LongField();
                        }
                        break;
                    case Types.VARCHAR:
                        field = new StringField();
                        break;
                    case Types.NUMERIC:
                        if (columns.getScale(i) > 0) {
                            field = new DoubleField();
                        } else {
                            field = new LongField();
                        }
                        break;
                    default:
                        field = new StringField();
                        break;
                }
                field.setName(name);
                fields[i - 1] = field;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void importRs(ResultSet rs) {
        ResultSetMetaData columns;
        try {
            columns = rs.getMetaData();
            fields = new Field[columns.getColumnCount()];
            for (int i = 1; i <= fields.length; i++) {
                String name = columns.getColumnLabel(i);
                Field field = null;
                switch (columns.getColumnType(i)) {
                    case Types.TIMESTAMP:
                    case Types.TIME:
                    case Types.DATE:
                        field = new DateField();
                        try {
                            ((DateField)field).setValue(new Date(rs.getTimestamp(i).getTime()));
                        } catch (NullPointerException e) {
                            ((DateField)field).setValue(null);
                        }
                        break;

                    case Types.INTEGER:
                        field = new IntField();
                        ((IntField)field).setValue(rs.getInt(i));
                        break;
                    case Types.BIGINT:
                        field = new LongField();
                        ((LongField)field).setValue(rs.getLong(i));
                        break;
                    case Types.FLOAT:
                        field = new FloatField();
                        ((FloatField)field).setValue(rs.getFloat(i));
                        break;
                    case Types.DOUBLE:
                        field = new DoubleField();
                        ((DoubleField)field).setValue(rs.getDouble(i));
                        break;
                    case Types.DECIMAL:
                        if (columns.getScale(i) > 0) {
                            field = new DoubleField();
                            ((DoubleField)field).setValue(rs.getDouble(i));
                        } else {
                            field = new LongField();
                            ((LongField)field).setValue(rs.getLong(i));
                        }
                        break;
                    case Types.VARCHAR:
                        field = new StringField();
                        ((StringField)field).setValue(rs.getString(i));
                        break;
                    case Types.NULL:
                        field = new StringField();
                        ((StringField)field).setValue("");
                        break;
                    case Types.NUMERIC:
                        if (columns.getScale(i) > 0) {
                            field = new DoubleField();
                            ((DoubleField)field).setValue(rs.getDouble(i));
                        } else {
                            field = new LongField();
                            ((LongField)field).setValue(rs.getLong(i));
                        }
                        break;
                    /**
                     * Add the handler for "TEXT" type
                     *         in mysql.. populate to String.
                     */
                    case Types.CLOB:
                        try {
                            field = new StringField();
                            Clob clob = rs.getClob(i);
                            int clobLength = (int)clob.length();
                            char[] buff = new char[BUFFLENGTH];
                            StringBuffer sb = new StringBuffer();
                            Reader reader = clob.getCharacterStream();
                            int read = reader.read(buff);
                            int totalRead = read;
                            sb.append(buff);
                            while (totalRead < clobLength && read >= 0) {
                                read = reader.read(buff);
                                totalRead += read;
                                sb.append(buff);
                            }
                            ((StringField)field).setValue(sb.toString());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            // e.printStackTrace();
                            field = new StringField();
                            ((StringField)field).setValue("");
                        }
                        break;
                    default:
                        field = new StringField();
                        ((StringField)field).setValue(rs.getString(i));
                        break;
                }
                field.setName(name);
                fields[i - 1] = field;
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Row.class).info(e.getMessage());
            e.printStackTrace();
        }
    }

    public Field getField(int pos) {
        Field ret = null;
        try {
            ret = fields[pos];
        } catch (NullPointerException ex) {
            // ex.printStackTrace() ;
            fields = new Field[pos + 1];
            for (int cnt = 0; cnt < pos + 1; cnt++) {
                fields[cnt] = new StringField();
                ((StringField)fields[cnt]).setName("Field" + (fields.length - 1));
                ((StringField)fields[cnt]).setValue("");
            }
            return fields[pos];
        } catch (IndexOutOfBoundsException e) {
            // e.printStackTrace();
            int oldLen = fields.length;
            int num = pos - fields.length + 1;
            Field[] tmp = new Field[pos + 1];
            System.arraycopy(fields, 0, tmp, 0, fields.length);
            fields = tmp;
            tmp = null;
            for (int cnt = oldLen; cnt < pos + 1; cnt++) {
                fields[cnt] = new StringField();
                ((StringField)fields[cnt]).setName("Field" + (fields.length - 1));
                ((StringField)fields[cnt]).setValue("");
            }
            return fields[pos];
        }
        return ret;
    }

    public Field getField(String name) {
        Field ret = null;
        if (fields != null) {
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getName().equalsIgnoreCase(name)) {
                    ret = fields[i];
                    break;
                }
            }
        }
        return ret;
    }

    public String[] getFieldNames() {
        String[] ret = null;
        if (null != fields) {
            ret = new String[fields.length];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = fields[i].getName();
            }
        }
        return ret;
    }

    public Field[] getFields() {
        return fields;
    }

    public Map toHashMap() {
        Map ret = new HashMap<String, Object>();
        for (int i = 0; i < fields.length; i++) {
            ret.put(fields[i].getName(), fields[i].getValue());
        }
        return ret;
    }
}