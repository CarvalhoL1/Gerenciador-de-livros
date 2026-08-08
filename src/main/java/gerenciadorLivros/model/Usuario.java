package gerenciadorLivros.model;

public class Usuario {
    int id;
    String nome;
    String email;

    public Usuario(int id, String nome, String email){
        setId(id);
        setNome(nome);
        setEmail(email);
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
