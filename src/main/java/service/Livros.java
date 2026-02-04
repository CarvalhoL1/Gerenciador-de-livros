package service;

import db.Conectar;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import service.Sessao;
import service.Contas.*;

public class Livros {
    public class manipularDB{
        public static class livro {
            int id;
            String titulo;
            String descricao;
            int total_pag;
            String status;
            int paginaAtual;

            public livro(int id, String titulo, String descricao, int total_pag, String status, int paginaAtual) {
                this.id = id;
                this.titulo = titulo;
                this.descricao = descricao;
                this.total_pag = total_pag;
                this.status = status;
                this.paginaAtual = paginaAtual;
            }

            public int getId() { return id; }
            public String getTitulo() { return titulo; }
            public String getDescricao() { return descricao; }
            public int getTotal_pag() { return total_pag; }
            public String getStatus() { return status; }
            public int getPaginaAtual() { return paginaAtual; }
        }

        public static void add_livro(String titulo, String descricao, int total_pag) throws SQLException {
            if (Sessao.usuarioLogado == null) {
                throw new IllegalStateException("Nenhum usuário logado.");
            }
            String insertSQL = "INSERT INTO livros (id_usuario, titulo, descricao, total_paginas) VALUES (?, ?, ?, ?)";
            try (Connection connection = Conectar.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                pstmt.setInt(1, Sessao.usuarioLogado.getId());
                pstmt.setString(2, titulo);
                pstmt.setString(3, descricao);
                pstmt.setInt(4, total_pag);

                pstmt.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static List<manipularDB.livro> listarMeusLivros() throws SQLException {
        String sql = "SELECT id, titulo, descricao, total_paginas, status, pagina_atual " +
                "FROM livros WHERE id_usuario = ? ORDER BY atualizado_em DESC";

        List<manipularDB.livro> lista = new ArrayList<>();

        try (Connection connection = Conectar.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, Sessao.usuarioLogado.getId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new manipularDB.livro(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("descricao"),
                            rs.getInt("total_paginas"),
                            rs.getString("status"),
                            rs.getInt("pagina_atual")
                    ));
                }
            }
        }
        return lista;
    }

}
