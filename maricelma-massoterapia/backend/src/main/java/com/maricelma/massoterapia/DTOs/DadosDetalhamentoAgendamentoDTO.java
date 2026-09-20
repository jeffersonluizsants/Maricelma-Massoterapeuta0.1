package com.maricelma.massoterapia.DTOs;

import com.maricelma.massoterapia.Modelos.Agendamento;

import java.time.LocalDateTime;

public record DadosDetalhamentoAgendamentoDTO(

        Long id,
        Long clienteId,
        String clienteNome,
        Long servicoId,
        String servicoNome,
        Long planoId,
        LocalDateTime dataHora,
        Agendamento.StatusAgendamento status
) {
    public DadosDetalhamentoAgendamentoDTO (Agendamento agendamento){
        this(
                agendamento.getId(),
                agendamento.getCliente().getId(),
                agendamento.getCliente().getNome(),
                agendamento.getServico().getId(),
                agendamento.getServico().getNome(),
                agendamento.getPlano() != null ? agendamento.getPlano().getId() : null,
                agendamento.getDataHora(),
                agendamento.getStatus()
        );
    }
}
