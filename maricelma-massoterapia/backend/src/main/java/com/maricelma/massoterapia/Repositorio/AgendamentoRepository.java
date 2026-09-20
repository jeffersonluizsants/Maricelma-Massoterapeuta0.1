package com.maricelma.massoterapia.Repositorio;

import com.maricelma.massoterapia.Modelos.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
            @Param("ServicoId") Long servicoId,
            @Param("Inicio")LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
            );
}
