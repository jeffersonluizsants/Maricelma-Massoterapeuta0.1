package com.maricelma.massoterapia.Repositorio;

import com.maricelma.massoterapia.Modelos.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    @Query("""
        SELECT a FROM Agendamento a 
        WHERE a.servico.id = :servicoId 
        AND a.status = 'AGENDADO'
        AND (:inicio < FUNCTION('TIMESTAMPADD', MINUTE, a.servico.duracaoMinutos, a.dataHora))
        AND (:fim > a.dataHora)
    """)
    List<Agendamento> findConflitosDeHorario(
            @Param("servicoId") Long servicoId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

    @Query("""
            SELECT COALESCE(SUM(a.servico.valor), 0)
            FROM Agendamento a 
            WHERE a.status = 'REALIZADO'
            AND CAST(a.dataHora AS localdate) BETWEEN :inicio AND :fim
            """)
    BigDecimal somarFaturamentoPorPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    @Query("""
            SELECT COUNT(a)
            FROM Agendamento a
            WHERE a.status = :status
            AND CAST(a.dataHora AS localdate) BETWEEN :inicio AND :fim
            """)
    Integer contarPorStatusEPeriodo(@Param("status") Agendamento.StatusAgendamento status, @Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}