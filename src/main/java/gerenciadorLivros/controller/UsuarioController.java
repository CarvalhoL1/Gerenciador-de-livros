package gerenciadorLivros.controller;

import gerenciadorLivros.dto.AlterarNomeRequest;
import gerenciadorLivros.dto.AlterarSenhaRequest;
import gerenciadorLivros.dto.DeletarContaRequest;
import gerenciadorLivros.dto.UsuarioResponse;
import gerenciadorLivros.model.Usuario;
import gerenciadorLivros.service.SessaoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gerenciadorLivros.service.ContasService;

import java.sql.SQLException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {
    private final ContasService contasService;
    private final SessaoService sessaoService;
    public UsuarioController(ContasService contasService, SessaoService sessaoService) {
        this.contasService = contasService;
        this.sessaoService = sessaoService;
    }
    @PutMapping("/usuarios/senha")
    public ResponseEntity<?> alterarSenha(@Valid @RequestBody AlterarSenhaRequest request, HttpServletRequest httpRequest) throws SQLException {
        String header = httpRequest.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token ausente.");
        }
        String token = header.substring(7);
        Usuario usuarioLogado = sessaoService.buscarPorToken(token);
        if (usuarioLogado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sessão inválida.");
        }
        boolean senhaAtualCorreta = contasService.autenticar(request.senhaAtual(), request.email());
        if (!senhaAtualCorreta) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha atual incorreta.");
        }
        contasService.editarSenha(request.email(), request.senhaNova());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/usuarios/nome")
    public ResponseEntity<?> alterarNome(@Valid @RequestBody AlterarNomeRequest request, HttpServletRequest httpRequest) throws SQLException {
        String header = httpRequest.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token ausente.");
        }
        String token = header.substring(7);
        Usuario usuarioLogado = sessaoService.buscarPorToken(token);
        if (usuarioLogado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sessão inválida.");
        }
        contasService.editarNome(usuarioLogado.getEmail(), request.nomeNovo());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuarios/eu")
    public ResponseEntity<?> meuPerfil(HttpServletRequest httpRequest) {
        String header = httpRequest.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token ausente.");
        }
        Usuario usuarioLogado = sessaoService.buscarPorToken(header.substring(7));
        if (usuarioLogado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sessão inválida.");
        }
        return ResponseEntity.ok(new UsuarioResponse(usuarioLogado.getId(), usuarioLogado.getNome(), usuarioLogado.getEmail()));
    }

    @PostMapping("/usuarios/deletar")
    public ResponseEntity<?> deletarConta(@Valid @RequestBody DeletarContaRequest request, HttpServletRequest httpRequest) throws SQLException {
        String header = httpRequest.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token ausente.");
        }
        String token = header.substring(7);
        Usuario usuarioLogado = sessaoService.buscarPorToken(token);
        if (usuarioLogado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sessão inválida.");
        }
        boolean senhaCorreta = contasService.autenticar(request.senha(), usuarioLogado.getEmail());
        if (!senhaCorreta) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta.");
        }
        contasService.deletarConta(usuarioLogado.getEmail());
        sessaoService.logout(token);
        return ResponseEntity.noContent().build();
    }
}
