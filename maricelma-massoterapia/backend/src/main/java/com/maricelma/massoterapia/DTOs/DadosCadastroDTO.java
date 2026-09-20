package com.maricelma.massoterapia.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroDTO(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String senha

) {
}
