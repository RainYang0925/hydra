
package com.xiaohongshu.automation.service.db;

import com.xiaohongshu.automation.config.PropertiesReader;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;

public class DBUtil {

    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    private static final DBUtil inst = new DBUtil();

    public static final DBUtil getInstance() {
        return inst;
    }

    private DBUtil() {
    }

    /**
     * Get Oracle connection by ...
     * @param jdbcURL
     * @param dbUser
     * @param dbPassword
     * @return
     */
    public Connection getConnection(String jdbcURL, String dbUser, String dbPassword) {
        Connection cn = null;
        try {
            Class.forName(MYSQL_DRIVER);
            cn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        }
        catch (ClassNotFoundException e) {
            LoggerFactory.getLogger(DBUtil.class).error("Can't initial mysql Driver.");
            e.printStackTrace();
        }
        catch (SQLException e) {
            LoggerFactory.getLogger(DBUtil.class).error("Cannot create SQL connection, check the JDBCURL[" + jdbcURL
                    + "] and DB_USER[" + dbUser + "]/Pass[" + dbPassword + "].");
            e.printStackTrace();
        }
        return cn;
    }

    public Connection getConnectionWith(String properties) {
        String jdbcHost;
        String jdbcName;
        String dbUser=null;
        String dbPassword =null;
        String jdbcURL = null;
        try {
            PropertiesReader propertiesReader = new PropertiesReader("jdbc.env.properties");
            jdbcHost = propertiesReader.loadValue(properties+".host");
            jdbcName =propertiesReader.loadValue(properties+".name");

            dbUser=propertiesReader.loadValue(properties+".user");
            dbPassword=propertiesReader.loadValue(properties+".password");

            jdbcURL= "jdbc:mysql://"+jdbcHost+"/"+jdbcName;
            LoggerFactory.getLogger(DBUtil.class).info("Loading " + properties + " 's db properties");
            System.out.println(jdbcURL);
        } catch (Exception e) {
            LoggerFactory.getLogger(DBUtil.class).error("Can't find properties file(jdbc.env.properties).");
            e.printStackTrace();
        }

        Connection cn = null;
        try {
            Class.forName(MYSQL_DRIVER);
            cn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        }
        catch (ClassNotFoundException e) {
            LoggerFactory.getLogger(DBUtil.class).error("Can't initial mysql Driver.");
            e.printStackTrace();

        }
        catch (SQLException e) {
            LoggerFactory.getLogger(DBUtil.class).error("Cannot create SQL connection, check the JDBCURL[" + jdbcURL
                    + "] and DB_USER[" + dbUser + "]/Pass[" + dbPassword + "].");
            e.printStackTrace();

        }
        return cn;
    }

    public boolean executeSQL(Connection con, String sql) {
        boolean ret = false;

        try {
            LoggerFactory.getLogger(DBUtil.class).info("prepare to execute sql:" + sql);
            PreparedStatement pst = con.prepareStatement(sql);
            ret = pst.execute();
            ret = true ;
        } catch (SQLException e) {
            LoggerFactory.getLogger(DBUtil.class).error("execute sql exception: " + sql);
            e.printStackTrace();
        } finally {
            DBUtil.getInstance().releaseConnection(con);
        }

        return ret;
    }

    public boolean executeSQL(Connection con, String sql, Object[] params) {
        boolean ret = false;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            LoggerFactory.getLogger(DBUtil.class).info("The DBUtil execute SQL is :" + sql);
            st = con.prepareStatement(sql);
            for (int i = 1; i <= params.length; i++) {
                String typeName = params[i - 1].getClass().getSimpleName();
                if (typeName.equalsIgnoreCase("string")) {
                    st.setString(i, (String)params[i - 1]);
                } else if (typeName.equalsIgnoreCase("int") || typeName.equalsIgnoreCase("integer")) {
                    st.setInt(i, (Integer)params[i - 1]);
                } else if (typeName.equalsIgnoreCase("date")) {
                    st.setDate(i, (Date)params[i - 1]);
                } else if (typeName.equalsIgnoreCase("float")) {
                    st.setFloat(i, (Float)params[i - 1]);
                } else if (typeName.equalsIgnoreCase("double")) {
                    st.setDouble(i, (Double)params[i - 1]);
                } else {
                    st.setString(i, params[i - 1].toString());
                }
            }
            ret = st.execute();
        } catch (Exception e) {
            LoggerFactory.getLogger(DBUtil.class).error("execute sql exception: " + sql);
            e.printStackTrace();
        } finally {
            if (st != null)
                try {
                    st.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            try {
                DBUtil.getInstance().releaseConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public void releaseConnection(Connection con) {
        try {
            if (null != con && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con = null;
        }
    }

    public Row[] executeQuery(Connection cn, String sql) {
        Vector ret = new Vector();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = cn.createStatement();
            LoggerFactory.getLogger(DBUtil.class).info(sql);
            rs = st.executeQuery(sql);
            while (rs != null && rsNext(rs)) {
                Row row = new Row();
                row.importRs(rs);
                ret.add(row);
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(DBUtil.class).error("execute query exception: " + sql);
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (st != null)
                try {
                    st.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            try {
                DBUtil.getInstance().releaseConnection(cn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (Row[])ret.toArray(new Row[0]);
    }

    public Row[] executeQuery(Connection con, String sql, Object[] params) {
        Vector ret = new Vector();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            LoggerFactory.getLogger(DBUtil.class).info("The DBUtil execute SQL is :" + sql);
            st = con.prepareStatement(sql);
            for (int i = 1; i <= params.length; i++) {
                String typeName = params[i - 1].getClass().getSimpleName();
                if (typeName.equalsIgnoreCase("string")) {
                    st.setString(i, (String)params[i - 1]);
                } else if (typeName.equalsIgnoreCase("int") || typeName.equalsIgnoreCase("integer")) {
                    st.setInt(i, (Integer)params[i - 1]);
                } else if (typeName.equalsIgnoreCase("date")) {
                    st.setDate(i, (Date)params[i - 1]);
                } else if (typeName.equalsIgnoreCase("float")) {
                    st.setFloat(i, (Float)params[i - 1]);
                } else if (typeName.equalsIgnoreCase("double")) {
                    st.setDouble(i, (Double)params[i - 1]);
                } else {
                    st.setString(i, params[i - 1].toString());
                }
            }
            rs = st.executeQuery();
            while (rsNext(rs)) {
                Row row = new Row();
                row.importRs(rs);
                ret.add(row);
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(DBUtil.class).error("execute query exception: " + sql);
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (st != null)
                try {
                    st.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            try {
                DBUtil.getInstance().releaseConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (Row[])ret.toArray(new Row[0]);
    }

    private static boolean rsNext(ResultSet rs) {
        boolean ret = true;
        try {
            if (!rs.next()) {
                ret = false;
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(DBUtil.class).error("sql exception: " + e.getMessage());
            e.printStackTrace();
            ret = false;
        }
        return ret;
    }

    public Collection convertRowArray(Row[] rows) {
        Collection ret = new ArrayList<Map>();
        if (null != rows)
            for (int i = 0; i < rows.length; i++) {
                ret.add(rows[i].toHashMap());
            }
        return ret;
    }

    public static void main(String[] arg) {

//        String jdbcURL = "jdbc:oracle:thin:@16.155.192.51:1521:mogush16";
//        String dbUser = "SHQA_PURGE_PROGRAM";
//        String dbPassword = "SHQA_PURGE_PROGRAM";
//        String sql =insert into AcActivity(name) values (\"测试测试测试\");
//                "select * from Test_Staffing_Profile minus insert into AcActivity(name) values (\"测试测试测试\"); t.staffing_profile_id,t.staffing_profile_name,t.creation_date,s.meaning from RSC_STAFFING_PROFILES t,KNTA_LOOKUPS_NLS s where t.status_code = s.lookup_code and s.lookup_type = 'RSC - Staffing Profile Status'";
//        Connection cn = DBUtil.getInstance().getConnection(jdbcURL, dbUser, dbPassword);
//        if ((DBUtil.getInstance().executeQuery(cn, sql).length == 0))
//            System.out.println("Pass...");
//        else
//            System.out.println("Failed...");

    //    String jdbcURL = "jdbc:mysql://10.13.2.16:3306/mogujie";
    //    String dbUser = "test";
     //   String dbPassword = "test123";
  //      String sql = "select * from AcActivity where id=50";
 //       String sql = "insert into AcActivity(name) values (\"测试测试测试\");";
         //String sql = "insert into mogujie.HonorMobile (userId,domain,areaCode,mobile) values ('93349126','mogujie','86','18503840117')";
        String sql = "select * from mogujie.HonorMobile";

        //Connection cn = DBUtil.getInstance().getConnection(jdbcURL,dbUser,dbPassword);
        Connection cn = DBUtil.getInstance().getConnectionWith("users");
        boolean result = DBUtil.getInstance().executeSQL(cn, sql);
        System.out.println(result);

    }

}
