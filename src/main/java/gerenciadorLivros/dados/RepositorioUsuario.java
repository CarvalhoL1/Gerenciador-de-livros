package gerenciadorLivros.dados;

import gerenciadorLivros.db.Conectar;
import gerenciadorLivros.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import gerenciadorLivros.service.ProtedorSenhas;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RepositorioUsuario implements IRepositorioUsuario{

    @Override
    public void add_usuario(String nome, String email, String senha) throws SQLException {
        String insertSQL = "INSERT INTO usuarios (nome, email, senha_hash) VALUES (?, ?, ?)";
        String hash = ProtedorSenhas.hashPassword(senha);
        try (Connection connection = Conectar.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, email);
            pstmt.setString(3, hash);

            pstmt.executeUpdate();
        }
    }

    @Override
    public Usuario login(String email, String senha) throws SQLException{
        String selectSQL = "SELECT id, nome, email, senha_hash FROM usuarios WHERE email = ?";

        try (Connection connection = Conectar.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();


            if (!rs.next()) return null;
            String senhaHash = rs.getString("senha_hash");
            if (!BCrypt.checkpw(senha, senhaHash)) {
                return null;
            }

            return new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email")
            );
        }
    }

    @Override
    public void deletar_conta(String email) throws SQLException{
        //query simples de deletar usuario, confere quantas linhas foram afetadas no banco depois do delete
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

    @Override
    public boolean verificarContaExiste(String email){
        String sql = "SELECT 1 FROM usuarios WHERE email = ?";
        try (Connection conn = Conectar.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                //tenta achar 1 usuario com esse email, se achar, retorna verdadeiro, se não falso
                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //caso o banco falhe, retorna falso
        return false;
    }

    @Override
    public boolean autenticar (String senhaDigitada, String email){
        String sql = "SELECT senha_hash FROM usuarios WHERE email = ?";
        try (Connection conn = Conectar.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String senhaHash = rs.getString("senha_hash");
                    if (senhaHash != null && BCrypt.checkpw(senhaDigitada, senhaHash)) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String EditarSenha(String email, String senha_nova) throws SQLException{
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

    @Override
    public String EditarNome(String email, String nome_novo) throws SQLException {
        String insertSQL = "UPDATE usuarios SET nome = ? WHERE email = ?";
        try (Connection connection = Conectar.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, nome_novo);
            pstmt.setString(2, email);
            int linhasAfetadas = pstmt.executeUpdate();
            if (linhasAfetadas == 0) {
                return "Falha ao mudar o nome";
            } else {
                return "Nome alterado! será atualizado na sua proxima secção";
            }
        }
    }

}
