package cz.DBProjekt.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static volatile Connection conn;
    private DBConnection() {}

    public static Connection connect() {
        if (conn == null) {
            synchronized (DBConnection.class) {
                if (conn == null) {
                    try {
                        Class.forName("org.sqlite.JDBC");
                        conn = DriverManager.getConnection("jdbc:sqlite:db/knihovna.db");
                    } catch (SQLException | ClassNotFoundException e) {
                        System.out.println(e);
                    }
                }
            }
        }
        return conn;
    }

    public static void disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
