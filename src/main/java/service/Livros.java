package service;

import db.Conectar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import service.Sessao;
import service.Contas.*;

public class Livros {
    public class manipularDB{
        public static class livro{
            int id;
            String titulo;
            String descricao;
            int total_pag;
            public livro(int id, String titulo, String descricao, int total_pag){
                this.descricao = descricao;
                this.titulo = titulo;
                this.id = id;
                this.total_pag = total_pag;
            }
            public int getTotal_pag() {return total_pag;}
            public int getId() {return id;}
            public String getTitulo() {return titulo;}
            public String getDescricao() {return descricao;}
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
}
