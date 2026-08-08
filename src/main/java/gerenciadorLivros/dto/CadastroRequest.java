package gerenciadorLivros.dto;

import jakarta.validation.constraints.NotBlank;

public record CadastroRequest(
    @NotBlank String email,
    @NotBlank String senha,
    @NotBlank String nome
){}
