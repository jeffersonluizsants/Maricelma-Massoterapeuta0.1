package com.maricelma.massoterapia.DTOs;

import java.math.BigDecimal;

public record DashboardFinanceiroDTO(
        BigDecimal faturamentoBruto,
        BigDecimal totalDespesas,
        BigDecimal lucroLiquido,
        Integer totalAtendimentoRealizados,
        Integer totalAgendamentosPendentes
) {
}
