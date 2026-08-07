package dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioResponse(Integer id, String nome, String email) {
}