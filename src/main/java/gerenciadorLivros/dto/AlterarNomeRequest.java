package gerenciadorLivros.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AlterarNomeRequest(
        @Email @NotBlank String email,
        @NotBlank String nomeNovo
) {}