package com.maricelma.massoterapia.DTOs;

import com.maricelma.massoterapia.Modelos.Despesas;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosCadastroDespesasDTO(
        @NotBlank String descricao,
        @NotNull @Positive BigDecimal valor,
        @NotNull LocalDate dataPagamento,
        @NotNull Despesas.CategoriaDespesa categoria
        ) {
}
