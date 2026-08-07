package gerenciadorLivros.controller;

import gerenciadorLivros.dto.CadastroRequest;
import gerenciadorLivros.dto.LoginRequest;
import gerenciadorLivros.dto.LoginResponse;
import gerenciadorLivros.dto.UsuarioResponse;
import gerenciadorLivros.model.Usuario;
import gerenciadorLivros.service.SessaoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import gerenciadorLivros.service.ContasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    private final ContasService contasService;
    private final SessaoService sessaoService;
    public AuthController(ContasService contasService, SessaoService sessaoService) {
        this.contasService = contasService;
        this.sessaoService = sessaoService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) throws SQLException {
        Usuario usuario = contasService.login(request.email(), request.senha());
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha incorretos.");
        }
        String token = sessaoService.criarSessao(usuario);
        return ResponseEntity.ok(new LoginResponse(token, new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail())));
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
