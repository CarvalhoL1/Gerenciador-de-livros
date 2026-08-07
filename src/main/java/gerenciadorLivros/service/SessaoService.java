package gerenciadorLivros.service;

import gerenciadorLivros.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessaoService {
    private final Map<String, Usuario> sessoesAtivas = new ConcurrentHashMap<>();

    public String criarSessao(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nulo");
        }
        String token = UUID.randomUUID().toString();
        sessoesAtivas.put(token, usuario);
        return token;
    }

    public Usuario buscarPorToken(String token) {
        return sessoesAtivas.get(token);
    }

    public boolean tokenValido(String token) {
        return sessoesAtivas.containsKey(token);
    }

    public void logout(String token) {
        sessoesAtivas.remove(token);
    }
}