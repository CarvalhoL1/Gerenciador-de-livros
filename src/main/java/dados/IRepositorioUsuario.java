package dados;

import model.Usuario;
import service.ContasService;

import java.sql.SQLException;


public interface IRepositorioUsuario {
    void add_usuario(String nome, String email, String senha) throws SQLException;
    Usuario login(String email, String senha, int id) throws SQLException;
    void deletar_conta(String email) throws SQLException;
    Boolean verificarContaExiste(String email) throws SQLException;
    Boolean autenticar (String senhaDigitada, int id) throws SQLException;
    String EditarSenha(String email, String senha_nova) throws SQLException;
    String EditarNome(String email, String nome_novo) throws SQLException;
}
