package com.maricelma.massoterapia.Repositorio;

import com.maricelma.massoterapia.Modelos.BloqueioAgenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BloqueioAgendaRepository extends JpaRepository<BloqueioAgenda, Long> {

    @Query("""
            SELECT b FROM BloqueioAgenda b
            WHERE (:inicio < b.dataHoraFim)
            AND (:fim > b.dataHoraInicio)
            """)
    List<BloqueioAgenda> findBloqueioConflitantes(
            @Param("inicio")LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
            );
 }
