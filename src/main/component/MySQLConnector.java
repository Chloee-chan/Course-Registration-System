package main.component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/course_system_management";

    public static Connection connect() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
