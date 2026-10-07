package com.maricelma.massoterapia.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DadosCadastroServicoDTO(
        @NotBlank String nome,
        String descricao,
        @NotNull @Positive Integer duracaoMinutos,
        @NotNull@Positive BigDecimal valor,
        Boolean possuiPlano
) {
}
