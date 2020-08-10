package com.gitHub.past.JDBC;

/**
 * 戴达
 */
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 使用JDBC工具类,获取数据库的连接 采用读取配置文件的方式 读取配置文件,获取连接,执行一次,static{}
 */
public class JDBCUtil {

    private static String dbUrl;

    private static String username;

    private static String password;

    private static String driverClassName;

    private static Connection con;


    static {
        try {
            readConfig();
            Class.forName(driverClassName);
            Logger.getAnonymousLogger().log(Level.INFO,"数据库连接成功");
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.INFO,"数据库连接失败");
        }
    }

    private static void readConfig() throws IOException {
        try (InputStream is = JDBCUtil.class.getClassLoader().getResourceAsStream("application.properties");) {
            Properties pro = new Properties();
            pro.load(is);
            String property = pro.getProperty("spring.profiles.active");
            try (InputStream is1 = JDBCUtil.class.getClassLoader().getResourceAsStream("application-"+property+".properties");){
                pro.load(is1);
                driverClassName = pro.getProperty("spring.datasource.driver-class-name");
                dbUrl = pro.getProperty("spring.datasource.url");
                username = pro.getProperty("spring.datasource.username");
                password = pro.getProperty("spring.datasource.password");
            }
        }
    }

    public static Connection getConnection() {
        try {
            con = DriverManager.getConnection(dbUrl+"&useAffectedRows=true", username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void close(Connection con, Statement stat) {

        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException ex) {
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
            }
        }

    }

    public static void close(Connection con, PreparedStatement pst , Statement stat, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
