package com.maricelma.massoterapia.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosCriarBloqueioDTO(
        @NotBlank String motivo,
        @NotNull LocalDateTime dataHoraInicio,
        @NotNull LocalDateTime dataHoraFIm
        ) {}
