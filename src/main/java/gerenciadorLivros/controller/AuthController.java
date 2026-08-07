package gerenciadorLivros.controller;

import gerenciadorLivros.dto.CadastroRequest;
import gerenciadorLivros.dto.LoginRequest;
import gerenciadorLivros.dto.UsuarioResponse;
import gerenciadorLivros.model.Usuario;
import org.springframework.web.bind.annotation.RestController;
import gerenciadorLivros.service.ContasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;

@RestController
public class AuthController {
    private final ContasService contasService;

    public AuthController(ContasService contasService) {
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

    @PostMapping("/auth/cadastro")
    public ResponseEntity<?> cadastro(@RequestBody CadastroRequest request){
        if (request.email().isBlank() || request.senha().isBlank() || request.nome().isBlank()) {
            return ResponseEntity.badRequest().body("Preencha nome, email e senha.");
        }
        try {
            contasService.cadastro(request.nome(), request.email(), request.senha());
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no banco.");
        }
    }
}
