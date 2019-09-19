package utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLUtils {

    private final String DATABASE_URL = "jdbc:mysql://database-1.cwwiwjxwtcnh.us-west-2.rds.amazonaws.com:3306/artemisdb";
    private final String USERNAME = "admin";
    private final String PASSWORD = "nc8apCseJVtQc5l8d1Ifbc3NFNNp0qufyBB";

    private Connection conn;

    private Properties properties;

    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
        }
        return properties;
    }

    public Connection connect() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(DATABASE_URL, getProperties());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

