package gerenciadorLivros.dto;

import jakarta.validation.constraints.NotBlank;

public record AddLivroRequest(
   @NotBlank String titulo, String descricao, int totalPaginas
) {}
