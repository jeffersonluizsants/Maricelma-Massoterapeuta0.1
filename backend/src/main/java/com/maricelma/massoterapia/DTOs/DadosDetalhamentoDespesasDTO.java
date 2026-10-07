package com.maricelma.massoterapia.DTOs;

import com.maricelma.massoterapia.Modelos.Despesas;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosDetalhamentoDespesasDTO(
        Long id,
        String descricao,
        BigDecimal valor,
        LocalDate dataPagamento,
        Despesas.CategoriaDespesa categoria
) {
    public DadosDetalhamentoDespesasDTO(Despesas despesas) {
    this(despesas.getId(),despesas.getDescricao(),despesas.getValor(),despesas.getDataPagamento(),despesas.getCategoria());
    }
}
