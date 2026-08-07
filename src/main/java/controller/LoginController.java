package controller;

import dto.LoginRequest;
import dto.UsuarioResponse;
import model.Usuario;
import service.ContasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;

public class LoginController {
    private final ContasService contasService;

    public LoginController(ContasService contasService) {
        this.contasService = contasService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (request.email().isBlank() || request.senha().isBlank()) {
            return ResponseEntity.badRequest().body("Preencha email e senha.");
        }
        try {
            Usuario usuario = contasService.login(request.email(), request.senha());
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha incorretos.");
            }
            return ResponseEntity.ok(new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail()));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no banco.");
        }
    }
}
