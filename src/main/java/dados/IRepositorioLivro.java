package dados;

import model.Livro;

import java.sql.SQLException;
import java.util.List;

public interface IRepositorioLivro {
    void add_livro(Livro livro, int id) throws SQLException;
    void deletar_livro(int id) throws SQLException;
    void editarTitulo(int id, String titulo) throws SQLException;
    void editarDesricao(int id, String desc) throws SQLException;
    void editarPagTotal(int id, int pg) throws SQLException;
    void editarPagAtual(int id, int pg) throws SQLException;
    void editarStatus(int id, String status) throws SQLException;
    double calcularProgresso(int id) throws SQLException;
    List<Livro> listarMeusLivros(int id) throws SQLException;
}
