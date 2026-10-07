package com.maricelma.massoterapia.DTOs;

import com.maricelma.massoterapia.Modelos.HorarioFuncionamento;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

public record DadosHorarioFuncionamentoDTO(
        @NotNull DayOfWeek diaSemana,
        @NotNull LocalTime horaAbertura,
        @NotNull LocalTime horaFechamento,
        Boolean ativo
        ) {
    public DadosHorarioFuncionamentoDTO(HorarioFuncionamento horario) {
        this(horario.getDiaSemana(),horario.getHoraAbertura(),horario.getHoraFechamento(),horario.getAtivo());
    }
}
