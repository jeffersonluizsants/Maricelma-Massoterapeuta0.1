package com.maricelma.massoterapia.DTOs;

import com.maricelma.massoterapia.Modelos.BloqueioAgenda;

import java.time.LocalDateTime;

public record DadosDetalhamentoBloqueioDTO(
        Long id,
        String motivo,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim
) {
    public DadosDetalhamentoBloqueioDTO(BloqueioAgenda block) {
        this(block.getId(),block.getMotivo(),block.getDataHoraInicio(),block.getDataHoraFim());
    }
}
