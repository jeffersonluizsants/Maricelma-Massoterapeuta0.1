package com.maricelma.massoterapia.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacaoDTO(
@NotBlank @Email String email,
@NotBlank String senha
) {
}
