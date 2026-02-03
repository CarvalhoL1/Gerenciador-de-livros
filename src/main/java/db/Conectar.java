package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conectar {
    private static final String DATABASE_URL = "jdbc:sqlite:base.db";
    public static Connection getConnection() throws SQLException {return DriverManager.getConnection(DATABASE_URL);
    }
}
