package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.Conectar;

public class Contas {



    public class manipularDB {
        public static class Usuario {
            int id;
            String nome;
            String email;

            public Usuario(int id, String nome, String email) {
                this.id = id;
                this.nome = nome;
                this.email = email;
            }

            public int getId() { return id; }
            public String getNome() { return nome; }
            public String getEmail() { return email; }
        }
        public static void add_usuario(String nome, String email, String senha) throws SQLException{
            String insertSQL = "INSERT INTO usuarios (nome, email, senha_hash) VALUES (?, ?, ?)";
            String hash = ProtedorSenhas.hashPassword(senha);
            try (Connection connection = Conectar.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                pstmt.setString(1, nome);
                pstmt.setString(2, email);
                pstmt.setString(3, hash);

                pstmt.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public static Usuario login(String email, String senha) throws SQLException{
            String selectSQL = "SELECT id, nome, email, senha_hash FROM usuarios WHERE email = ?";

            try (Connection connection = Conectar.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {

                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();


                if (!rs.next()) return null;
                String hashSalvo = rs.getString("senha_hash");
                if (!ProtedorSenhas.checkPassword(senha, hashSalvo)) {
                    return null;
                }

                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email")
                );
            }
        }

        public static void deletar_conta(String email) throws SQLException{
            String deletSQL = "DELETE FROM usuarios WHERE email = ?";
            try (Connection connection = Conectar.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(deletSQL)) {

                pstmt.setString(1, email);
                int linhasAfetadas = pstmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Usuario deletado");
                }
                else{
                    System.out.println("Falha ao deletar usuario");
                }
            }
        }

        public static Usuario buscar_usuario(String email) throws SQLException{
            String selectSQL = "SELECT id, nome, email, frase, senha_hash FROM usuarios WHERE email = ?";
            try (Connection connection = Conectar.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()) return null;
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email")
                );
            }
        }

        public static String EditarSenha(String email, String senha_nova) throws SQLException{
            String insertSQL = "UPDATE usuarios SET senha_hash = ? WHERE email = ?";
            try (Connection connection = Conectar.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                String hash = ProtedorSenhas.hashPassword(senha_nova);
                pstmt.setString(1, hash);
                pstmt.setString(2, email);
                int linhasAfetadas = pstmt.executeUpdate();
                if (linhasAfetadas == 0) {
                    return "Falha ao mudar a senha";
                }
                else{
                    return "Senha alterada! será atualizado na sua proxima secção";
                }
            }
        }
        public static String EditarNome(String email, String nome_novo) throws SQLException{
            String insertSQL = "UPDATE usuarios SET nome = ? WHERE email = ?";
            try (Connection connection = Conectar.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                pstmt.setString(1, nome_novo);
                pstmt.setString(2, email);
                int linhasAfetadas = pstmt.executeUpdate();
                if (linhasAfetadas == 0) {
                    return "Falha ao mudar o nome";
                }
                else{
                    return "Nome alterado! será atualizado na sua proxima secção";
                }
            }
        }
    }
}
