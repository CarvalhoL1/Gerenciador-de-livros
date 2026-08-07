package gerenciadorLivros.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditarLivroRequest(
        @NotBlank String titulo,
        String descricao,
        @NotNull int pagAtual,
        @NotNull int pagTotal,
        String status
) {
}
