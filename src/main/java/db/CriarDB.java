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
    public static void criarTabLivros() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS livros (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_usuario INTEGER NOT NULL," +
                "titulo TEXT NOT NULL," +
                "descricao TEXT," +
                "total_paginas INTEGER CHECK(total_paginas > 0)," +
                "status TEXT NOT NULL DEFAULT 'quero_ler' " +
                "CHECK(status IN ('quero_ler','lendo','pausado','lido','abandonado'))," +
                "pagina_atual INTEGER DEFAULT 0 CHECK(pagina_atual >= 0)," +
                "atualizado_em TEXT DEFAULT (datetime('now'))," +
                "FOREIGN KEY(id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE);";
        try (Connection connection = Conectar.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());

        }
    }
}
