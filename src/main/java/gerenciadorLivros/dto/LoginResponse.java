package gerenciadorLivros.dto;

public record LoginResponse(String token, UsuarioResponse usuario) {
}