package com.maricelma.massoterapia.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DadosCriarPLanoDTO(

        @NotNull Long clienteId,
        @NotNull Long servicoId,
        @NotNull @Positive Integer totalSessoes,
        @NotNull @Positive BigDecimal valorTotal
        ) {
}
