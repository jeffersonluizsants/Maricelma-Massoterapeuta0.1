package com.maricelma.massoterapia.Repositorio;

import com.maricelma.massoterapia.Modelos.Despesas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface DespesasRepository extends JpaRepository<Despesas, Long> {
    List<Despesas> findByDataPagamentoBetween(LocalDate inicio, LocalDate fim);

    @Query("""
            SELECT COALESCE(SUM(d.valor), 0)
            FROM Despesa d
            WHERE d.dataPagamento BETWEEN :inicio AND :fim
            """)
    BigDecimal somarDespesasPorPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
