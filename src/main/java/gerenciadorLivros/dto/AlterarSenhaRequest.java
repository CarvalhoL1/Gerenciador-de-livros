package gerenciadorLivros.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AlterarSenhaRequest(
        @Email @NotBlank String email,
        @NotBlank String senhaAtual,
        @NotBlank String senhaNova
) {}