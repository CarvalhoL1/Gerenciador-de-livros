package gerenciadorLivros.controller;

import gerenciadorLivros.dto.AddLivroRequest;
import gerenciadorLivros.dto.EditarLivroRequest;
import gerenciadorLivros.model.Livro;
import gerenciadorLivros.model.Usuario;
import gerenciadorLivros.service.LivrosService;
import gerenciadorLivros.service.SessaoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class LivroController {
    private final LivrosService livrosService;
    private final SessaoService sessaoService;
    public LivroController(LivrosService livrosService, SessaoService sessaoService) {
        this.livrosService = livrosService;
        this.sessaoService = sessaoService;
    }

    @PostMapping("/livros")
    public ResponseEntity<?> cadastrarLivro(@Valid @RequestBody AddLivroRequest request, HttpServletRequest httpRequest) {
        String header = httpRequest.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token ausente.");
        }
        String token = header.substring(7);
        Usuario usuarioLogado = sessaoService.buscarPorToken(token);
        if (usuarioLogado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sessão inválida.");
        }

        try {
            livrosService.addLivro(request.titulo(), request.descricao(), request.totalPaginas(), usuarioLogado.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body("Livro cadastrado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no banco.");
        }
    }

    @PutMapping("/livros/{id}")
    public ResponseEntity<?> editarLivroGeral(@PathVariable int id, @Valid @RequestBody EditarLivroRequest request) {
        try {
            livrosService.editarTitulo(id, request.titulo());
            livrosService.editarDesricao(id, request.descricao());
            livrosService.editarPagAtual(id, request.pagAtual());
            livrosService.editarPagTotal(id, request.pagTotal());
            livrosService.editarStatus(id, request.status());

            return ResponseEntity.ok("Livro atualizado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no banco.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/livros")
    public ResponseEntity<?> listarLivros(HttpServletRequest request) throws SQLException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token ausente.");
        }
        String token = header.substring(7);
        Usuario usuarioLogado = sessaoService.buscarPorToken(token);
        if (usuarioLogado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sessão inválida.");
        }
        List<Livro> livros = livrosService.listarMeusLivros(usuarioLogado.getId());
        return ResponseEntity.ok(livros);
    }
    @DeleteMapping("/livros/{id}")
    public ResponseEntity<?> deletarLivro(@PathVariable int id, HttpServletRequest httpRequest) throws SQLException {
        String header = httpRequest.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token ausente.");
        }
        Usuario usuarioLogado = sessaoService.buscarPorToken(header.substring(7));
        if (usuarioLogado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sessão inválida.");
        }
        livrosService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }
}
