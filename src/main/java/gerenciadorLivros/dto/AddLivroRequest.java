package gerenciadorLivros.dto;

import jakarta.validation.constraints.NotBlank;

public record AddLivroRequest(
   @NotBlank String titulo,  @NotBlank String descricao, int total_pag, int idUsuario
) {}
