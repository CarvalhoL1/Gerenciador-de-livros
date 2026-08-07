package service;

import java.sql.SQLException;
import dados.IRepositorioUsuario;
import dados.RepositorioUsuario;
import model.Usuario;

public class ContasService {
    private static ContasService instance;
    private final IRepositorioUsuario repositorioUsuario;

    private ContasService(){
        this.repositorioUsuario = new RepositorioUsuario();
    }
    public static synchronized ContasService getInstance(){
        if(instance == null){
            instance = new ContasService();
        }
        return instance;
    }

    public void add_usuario(String nome, String email, String senha) throws SQLException{
        if(nome.isBlank() || email.isBlank() || senha.isBlank()){
            throw new IllegalArgumentException("Cadastro Invalido");
        }
        repositorioUsuario.add_usuario(nome, email, senha);
    }

    public Usuario login(String email, String senha, int id) throws SQLException{
        return repositorioUsuario.login(email, senha, id);
    }

    public void deletar_conta(String email) throws SQLException {
        if(email.isBlank()) {
           throw new IllegalArgumentException("Conta não encontrada");
        }
        repositorioUsuario.deletar_conta(email);
    }

    public boolean verificarContaExiste(String email) throws SQLException {
        return repositorioUsuario.verificarContaExiste(email);
    }

    public boolean autenticar(String senhaDigitada, int id) throws SQLException {
        return repositorioUsuario.autenticar(senhaDigitada, id);
    }

    public void EditarSenha(String email, String senha_nova) throws SQLException {
        if (email.isBlank() || senha_nova.isBlank()) {
            throw new IllegalArgumentException("Email ou senha invalida");
        }
        repositorioUsuario.EditarSenha(email, senha_nova);
    }

    public void EditarNome(String email, String nome_novo) throws SQLException {
        if (email.isBlank() || nome_novo.isBlank()) {
            throw new IllegalArgumentException("Email ou senha invalida");
        }
        repositorioUsuario.EditarNome(email, nome_novo);
    }


}
