package com.maricelma.massoterapia.DTOs;

import com.maricelma.massoterapia.Modelos.Servico;

import java.math.BigDecimal;

public record DadosDetalhamentoServicoDTO(
        Long id,
        String nome,
        String descricao,
        Integer duracaoMinutos,
        BigDecimal valor,
        Boolean possuiPlano
) {
    public DadosDetalhamentoServicoDTO(Servico servico) {
        this(servico.getId(), servico.getNome(), servico.getDescricao(),servico.getDuracaoMinutos(),servico.getValor(),servico.getPossuiPlano());
    }
}
