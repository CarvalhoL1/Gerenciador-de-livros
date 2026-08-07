package gerenciadorLivros.service;
import gerenciadorLivros.model.Usuario;

public class SessaoService {
    private static Usuario usuarioLogado;

    private SessaoService() {}

    public static void login(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nulo");
        }
        usuarioLogado = usuario;
    }

    public static void logout() {
        usuarioLogado = null;
    }

    public static boolean isLogado() {
        return usuarioLogado != null;
    }

    public static Usuario getUsuarioLogado() {
        if (usuarioLogado == null) {
            throw new IllegalStateException("Nenhum usuario logado");
        }
        return usuarioLogado;
    }
}