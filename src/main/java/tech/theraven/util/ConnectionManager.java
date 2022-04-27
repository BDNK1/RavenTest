package tech.theraven.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String URL = "spring.datasource.url";
    private static final String USER = "spring.datasource.username";
    private static final String PASS = "spring.datasource.password";



    private ConnectionManager() {
    }
    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL),PropertiesUtil.get(USER),PropertiesUtil.get(PASS)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
