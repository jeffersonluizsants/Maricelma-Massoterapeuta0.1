package com.maricelma.massoterapia.DTOs;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacaoDTO(
@NotBlank String nome,
@NotBlank String email,
@NotBlank String senha
) {
}
