package gerenciadorLivros.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditarLivroRequest(
        @NotBlank String titulo,
        String descricao,
        @NotNull Integer pagAtual,
        @NotNull Integer pagTotal,
        String status
) {
}
