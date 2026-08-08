package gerenciadorLivros.model;

public class Livro {
    int id;
    String titulo;
    String descricao;
    Integer total_pag;
    String status;
    Integer paginaAtual;

    public Livro(int id, String titulo, String descricao, Integer totalPag, String status, Integer paginaAtual) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.total_pag = totalPag;
        this.status = status;
        this.paginaAtual = paginaAtual;
    }
    public Livro(String titulo, String descricao, Integer total_pag) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.total_pag = total_pag;
    }
    //getters e setters do livro
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setTotal_pag(Integer total_pag) { this.total_pag = total_pag; }
    public void setStatus(String status) { this.status = status; }
    public void setPaginaAtual(Integer paginaAtual) { this.paginaAtual = paginaAtual; }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getTotalPag() {
        return total_pag;
    }

    public String getStatus() {
        return status;
    }

    public Integer getPaginaAtual() {
        return paginaAtual;
    }
}
