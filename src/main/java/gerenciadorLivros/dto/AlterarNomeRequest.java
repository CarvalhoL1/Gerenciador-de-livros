package gerenciadorLivros.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AlterarNomeRequest(
        @NotBlank String nomeNovo
) {}