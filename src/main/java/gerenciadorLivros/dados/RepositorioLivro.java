package gerenciadorLivros.dados;

import gerenciadorLivros.db.Conectar;
import gerenciadorLivros.model.Livro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioLivro implements IRepositorioLivro {

    public void add_livro(Livro livro, int id) throws SQLException {
        String insertSQL = "INSERT INTO livros (id_usuario, titulo, descricao, total_paginas) VALUES (?, ?, ?, ?)";
        try (Connection connection = Conectar.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, livro.getTitulo());
            pstmt.setString(3, livro.getDescricao());
            pstmt.setInt(4, livro.getTotal_pag());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar_livro(int id) throws SQLException {
        //mesma logica ultilizada nas contas, se nenhuma linha for afetada depois do delete, então deu erro
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

    public void editarTitulo(int id, String titulo) throws SQLException {
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

    public void editarDesricao(int id, String desc) throws SQLException {
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

    public void editarPagTotal(int id, int pg) throws SQLException {
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

    public void editarPagAtual(int id, int pg) throws SQLException {
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

    public void editarStatus(int id, String status) throws SQLException {
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
    public boolean eNumero(String str) {
        if (str == null) return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public double calcularProgresso(int id) throws SQLException  {
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
    public List<Livro> listarMeusLivros(int id) throws SQLException {
        // Busca todos os livros do usuário logado e retorna em uma lista
        String sql = "SELECT id, titulo, descricao, total_paginas, status, pagina_atual " +
                "FROM livros WHERE id_usuario = ? ORDER BY atualizado_em DESC";

        List<Livro> lista = new ArrayList<>();

        try (Connection connection = Conectar.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Livro(
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
