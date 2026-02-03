package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CriarDB {
    public static void criarTabUser() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "email TEXT UNIQUE NOT NULL," +
                "senha_hash TEXT NOT NULL);";
        try (Connection connection = Conectar.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());

        }
    }
}
