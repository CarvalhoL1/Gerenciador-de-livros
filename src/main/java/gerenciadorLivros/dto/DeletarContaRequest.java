package gerenciadorLivros.dto;

import jakarta.validation.constraints.NotBlank;

public record DeletarContaRequest(
        @NotBlank String senha
) {
}
