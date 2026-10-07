package com.maricelma.massoterapia.DTOs;

import com.maricelma.massoterapia.Modelos.PlanoTratamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosDetalhamentoPlanoDTO(
        Long id,
        Long clienteId,
        String clienteNome,
        Long servicoId,
        String servicoNome,
        Integer totalSessoes,
        Integer sessoesRestantes,
        BigDecimal valorTotal,
        LocalDateTime dataInicio
) {
    public DadosDetalhamentoPlanoDTO(PlanoTratamento plano) {
        this(
                plano.getId(),
                plano.getCliente().getId(),
                plano.getCliente().getNome(),
                plano.getServico().getId(),
                plano.getServico().getNome(),
                plano.getTotalSessoes(),
                plano.getSessoesRestantes(),
                plano.getValorTotal(),
                plano.getDataInicio());
    }
}
