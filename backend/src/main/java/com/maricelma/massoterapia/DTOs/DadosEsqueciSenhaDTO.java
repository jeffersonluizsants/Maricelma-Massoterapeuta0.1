package com.maricelma.massoterapia.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosEsqueciSenhaDTO(
        @NotBlank @Email String email
) {
}
