package gerenciadorLivros.controller;

import gerenciadorLivros.dto.AddLivroRequest;
import gerenciadorLivros.dto.EditarLivroRequest;
import gerenciadorLivros.service.LivrosService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class LivroController {
    private final LivrosService livrosService;

    public LivroController(LivrosService livrosService) {
        this.livrosService = livrosService;
    }

    @PostMapping("/livros/cadastrar")
    public ResponseEntity<?> cadastrarLivro(@Valid @RequestBody AddLivroRequest request){
        try {
            livrosService.addLivro(request.titulo(), request.descricao(), request.total_pag(), request.idUsuario());
            return ResponseEntity.status(HttpStatus.CREATED).body("Livro cadastrado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no banco.");
        }
    }

    @PutMapping("/livros/{id}")
    public ResponseEntity<?> editarLivroGeral(@PathVariable int id, @Valid @RequestBody EditarLivroRequest request) {
        try {
            // Executa os métodos que você já tem no service um por um
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

}
