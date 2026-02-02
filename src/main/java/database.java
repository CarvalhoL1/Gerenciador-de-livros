import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class database {
    public static void main(String[] args) {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "email TEXT UNIQUE NOT NULL," +
                "senha_hash TEXT NOT NULL);";
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db");
        Statement statement = connection.createStatement();){

            statement.setQueryTimeout(30);
            statement.executeUpdate(sql);
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
