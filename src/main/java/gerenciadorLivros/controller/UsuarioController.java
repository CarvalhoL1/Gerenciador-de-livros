package gerenciadorLivros.controller;

import gerenciadorLivros.dto.AlterarNomeRequest;
import gerenciadorLivros.dto.AlterarSenhaRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import gerenciadorLivros.service.ContasService;

import java.sql.SQLException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {
    private final ContasService contasService;

    public UsuarioController(ContasService contasService) {
        this.contasService = contasService;
    }

    @PutMapping("/usuarios/senha")
    public ResponseEntity<?> alterarSenha(@Valid @RequestBody AlterarSenhaRequest request) throws SQLException {
        boolean senhaAtualCorreta = contasService.autenticar(request.senhaAtual(), request.email());
        if (!senhaAtualCorreta) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha atual incorreta.");
        }
        contasService.editarSenha(request.email(), request.senhaNova());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/usuarios/nome")
    public ResponseEntity<?> alterarNome(@Valid @RequestBody AlterarNomeRequest request) throws SQLException {
        contasService.editarNome(request.email(), request.nomeNovo());
        return ResponseEntity.noContent().build();
    }
}
