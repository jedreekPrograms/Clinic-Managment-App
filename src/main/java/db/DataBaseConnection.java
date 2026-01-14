package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {

    private static final String URL =
            "jdbc:mariadb://localhost:3307/clinic_db";
    private static final String USER = "clinic_user";
    private static final String PASSWORD = "user123";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
