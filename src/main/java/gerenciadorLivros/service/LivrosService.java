package gerenciadorLivros.service;

import gerenciadorLivros.dados.IRepositorioLivro;
import gerenciadorLivros.dados.RepositorioLivro;
import gerenciadorLivros.model.Livro;
import java.sql.SQLException;
import java.util.List;

public class LivrosService {
    private static LivrosService instance;
    private final IRepositorioLivro repositorioLivro;

    private LivrosService(){
        this.repositorioLivro = new RepositorioLivro();
    }
    public static synchronized LivrosService getInstance(){
        if(instance == null){
            instance = new LivrosService();
        }
        return instance;
    }

    public void add_livro(String titulo, String descricao, int total_pag, int idUsuario) throws SQLException {
        if (titulo.isBlank()){
            throw  new IllegalArgumentException("Titulo não pode ser nulo");
        }
        repositorioLivro.add_livro(new Livro(titulo, descricao, total_pag), idUsuario);
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
