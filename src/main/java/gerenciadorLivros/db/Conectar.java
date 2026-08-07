package gerenciadorLivros.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conectar {
    private static final String DATABASE_URL = "jdbc:sqlite:base.gerenciadorLivros.db";
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL);
        System.out.println("-> Banco conectado em: " + connection.getMetaData().getURL());
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }
        return connection;
    }
    }

