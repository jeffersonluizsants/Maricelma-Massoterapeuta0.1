package com.maricelma.massoterapia.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroClienteDTO(
        @NotBlank String nome,
        @NotBlank String telefone,
        @NotBlank @Email String email
) {
}
