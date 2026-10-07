package com.maricelma.massoterapia.DTOs;

import jakarta.validation.constraints.NotBlank;

public record DadosRedefinirSenhaDTO(
        @NotBlank String token,
        @NotBlank String novaSenha
) {
}
