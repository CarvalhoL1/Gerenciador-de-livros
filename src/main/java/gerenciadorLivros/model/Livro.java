package gerenciadorLivros.model;

public class Livro {
    int id;
    String titulo;
    String descricao;
    int total_pag;
    String status;
    int paginaAtual;

    public Livro(int id, String titulo, String descricao, int total_pag, String status, int paginaAtual) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.total_pag = total_pag;
        this.status = status;
        this.paginaAtual = paginaAtual;
    }
    public Livro(String titulo, String descricao, int total_pag) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.total_pag = total_pag;
    }
    //getters e setters do livro
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setTotal_pag(int total_pag) { this.total_pag = total_pag; }
    public void setStatus(String status) { this.status = status; }
    public void setPaginaAtual(int paginaAtual) { this.paginaAtual = paginaAtual; }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getTotal_pag() {
        return total_pag;
    }

    public String getStatus() {
        return status;
    }

    public int getPaginaAtual() {
        return paginaAtual;
    }
}
