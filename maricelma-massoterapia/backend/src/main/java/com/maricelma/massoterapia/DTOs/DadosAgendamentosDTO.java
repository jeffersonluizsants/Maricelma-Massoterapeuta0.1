package com.maricelma.massoterapia.DTOs;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamentosDTO(
        @NotNull Long clienteId,
        @NotNull Long servicoId,
        Long planoId,
        @NotNull @Future LocalDateTime dataHora
        ) {
}
