package gerenciadorLivros.service;

import java.sql.SQLException;
import gerenciadorLivros.dados.IRepositorioUsuario;
import gerenciadorLivros.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class ContasService {
    private final IRepositorioUsuario repositorioUsuario;

    public ContasService(IRepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    public void cadastro(String nome, String email, String senha) throws SQLException{
        if(nome.isBlank() || email.isBlank() || senha.isBlank()){
            throw new IllegalArgumentException("Cadastro Invalido");
        }
        repositorioUsuario.add_usuario(nome, email, senha);
    }

    public Usuario login(String email, String senha) throws SQLException{
        return repositorioUsuario.login(email, senha);
    }

    public void deletarConta(String email) throws SQLException {
        if(email.isBlank()) {
           throw new IllegalArgumentException("Conta não encontrada");
        }
        repositorioUsuario.deletar_conta(email);
    }

    public boolean verificarContaExiste(String email) throws SQLException {
        return repositorioUsuario.verificarContaExiste(email);
    }

    public boolean autenticar(String senhaDigitada, String email) throws SQLException {
        return repositorioUsuario.autenticar(senhaDigitada, email);
    }

    public void editarSenha(String email, String senha_nova) throws SQLException {
        if (email.isBlank() || senha_nova.isBlank()) {
            throw new IllegalArgumentException("Email ou senha invalida");
        }
        repositorioUsuario.EditarSenha(email, senha_nova);
    }

    public void editarNome(String email, String nome_novo) throws SQLException {
        if (email.isBlank() || nome_novo.isBlank()) {
            throw new IllegalArgumentException("Email ou senha invalida");
        }
        repositorioUsuario.EditarNome(email, nome_novo);
    }


}
