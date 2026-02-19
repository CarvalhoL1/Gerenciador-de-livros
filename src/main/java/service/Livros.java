package service;

import db.Conectar;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import service.Sessao;
import service.Contas.*;

public class Livros {
    public static class manipularDB {
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
            public void setTitulo(String titulo) { this.titulo = titulo; }
            public void setDescricao(String descricao) { this.descricao = descricao; }
            public void setTotal_pag(int total_pag) { this.total_pag = total_pag; }
            public void setStatus(String status) { this.status = status; }
            public void setPaginaAtual(int paginaAtual) { this.paginaAtual = paginaAtual; }

            public int getId() {
                return id;
            }

            public String getTitulo() {
                return titulo;
            }

            public String getDescricao() {
                return descricao;
            }

            public int getTotal_pag() {
                return total_pag;
            }

            public String getStatus() {
                return status;
            }

            public int getPaginaAtual() {
                return paginaAtual;
            }
        }

        public static void add_livro(String titulo, String descricao, Integer total_pag) throws SQLException {
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void deletar_livro(int id) throws SQLException {
            String deletSQL = "DELETE FROM livros WHERE id = ?";
            try (Connection connection = Conectar.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(deletSQL)) {

                pstmt.setInt(1, id);
                int linhasAfetadas = pstmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Livro deletado");
                } else {
                    System.out.println("Falha ao deletar livro");
                }
            }
        }

        public static void editarTitulo(int id, String titulo) throws SQLException {
            String insertSQL = "UPDATE livros SET titulo = ? WHERE id = ?";
            try (Connection connection = Conectar.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                pstmt.setString(1, titulo);
                pstmt.setInt(2, id);
                int linhasAfetadas = pstmt.executeUpdate();
                if (linhasAfetadas == 0) {
                    System.out.println("Falha ao mudar titulo");
                } else {
                    System.out.println("Titulo alterado!");
                }
            }
        }

        public static void editarDesricao(int id, String desc) throws SQLException {
            String insertSQL = "UPDATE livros SET descricao = ? WHERE id = ?";
            try (Connection connection = Conectar.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                pstmt.setString(1, desc);
                pstmt.setInt(2, id);
                int linhasAfetadas = pstmt.executeUpdate();
                if (linhasAfetadas == 0) {
                    System.out.println("Falha ao mudar descrição");
                } else {
                    System.out.println("Descrição alterada!");
                }
            }
        }

        public static void editarPagTotal(int id, int pg) throws SQLException {
            String insertSQL = "UPDATE livros SET total_paginas = ? WHERE id = ?";
            try (Connection connection = Conectar.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                pstmt.setInt(1, pg);
                pstmt.setInt(2, id);
                int linhasAfetadas = pstmt.executeUpdate();
                if (linhasAfetadas == 0) {
                    System.out.println("Falha ao mudar pagina");
                } else {
                    System.out.println("Pagina alterada!");
                }
            }
        }

        public static void editarPagAtual(int id, int pg) throws SQLException {
            String insertSQL = "UPDATE livros SET pagina_atual = ? WHERE id = ?";
            try (Connection connection = Conectar.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                pstmt.setInt(1, pg);
                pstmt.setInt(2, id);
                int linhasAfetadas = pstmt.executeUpdate();
                if (linhasAfetadas == 0) {
                    System.out.println("Falha ao mudar pagina");
                } else {
                    System.out.println("Pagina alterada!");
                }
            }
        }

        public static void editarStatus(int id, String status) throws SQLException {
            String insertSQL = "UPDATE livros SET status = ? WHERE id = ?";
            try (Connection connection = Conectar.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                pstmt.setString(1, status);
                pstmt.setInt(2, id);
                int linhasAfetadas = pstmt.executeUpdate();
                if (linhasAfetadas == 0) {
                    System.out.println("Falha ao mudar status");
                } else {
                    System.out.println("Status alterado!");
                }
            }
        }
        public static boolean eNumero(String str) {
            if (str == null) return false;
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        public static double calcularProgresso(int id) throws SQLException  {
            String selectSQL = "SELECT total_paginas, pagina_atual FROM livros WHERE id = ?";
            try (Connection connection = Conectar.getConnection();
                 PreparedStatement ps = connection.prepareStatement(selectSQL)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    int total = rs.getInt("total_paginas");
                    int atual = rs.getInt("pagina_atual");
                    if (total == 0 || atual == 0) return 0;
                    else if (atual > total) return 100;
                    return (atual * 100.0) / total;
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

}
