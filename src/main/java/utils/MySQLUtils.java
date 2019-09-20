package utils;


import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLUtils {

    private Connection conn;

    public Connection connect() {
        if (conn == null) {
            try {
                MysqlDataSource dataSource = new MysqlDataSource();
                String USERNAME = "admin";
                dataSource.setUser(USERNAME);
                String PASSWORD = "nc8apCseJVtQc5l8d1Ifbc3NFNNp0qufyBB";
                dataSource.setPassword(PASSWORD);
                String DATABASE_URL = "database-1.cwwiwjxwtcnh.us-west-2.rds.amazonaws.com";
                dataSource.setServerName(DATABASE_URL);
                dataSource.setPort(3306);
                dataSource.setDatabaseName("artemisdb");
                conn = dataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    void disconnect() {
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

