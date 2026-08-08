package gerenciadorLivros.service;

import gerenciadorLivros.dados.IRepositorioLivro;
import gerenciadorLivros.dados.RepositorioLivro;
import gerenciadorLivros.model.Livro;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class LivrosService {
    private final IRepositorioLivro repositorioLivro;

    public LivrosService(IRepositorioLivro repositorioLivro){
        this.repositorioLivro = repositorioLivro;
    }

    public void addLivro(String titulo, String descricao, int totalPag, int idUsuario) throws SQLException {
        if (titulo.isBlank()){
            throw  new IllegalArgumentException("Titulo não pode ser nulo");
        }
        repositorioLivro.add_livro(new Livro(titulo, descricao, totalPag), idUsuario);
    }

    public void deletar_livro(int id) throws SQLException {
        repositorioLivro.deletar_livro(id);
    }

    public void editarTitulo(int id, String titulo) throws SQLException {
        if(titulo.isBlank()){
            throw new IllegalArgumentException("Preencha o titulo!");
        }
        repositorioLivro.editarTitulo(id, titulo);
    }

    public void editarDesricao(int id, String desc) throws SQLException {
        repositorioLivro.editarDesricao(id, desc);
    }

    public void editarPagTotal(int id, int pg) throws SQLException {
        repositorioLivro.editarPagTotal(id, pg);
    }

    public void editarPagAtual(int id, int pg) throws SQLException {
        repositorioLivro.editarPagAtual(id, pg);
    }

    public void editarStatus(int id, String status) throws SQLException {
        repositorioLivro.editarStatus(id, status);
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
    public double calcularProgresso(int id) throws SQLException  {
        return repositorioLivro.calcularProgresso(id);
    }
    public List<Livro> listarMeusLivros(int id) throws SQLException {
        return repositorioLivro.listarMeusLivros(id);
    }



}
