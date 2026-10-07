package com.maricelma.massoterapia.Repositorio;

import com.maricelma.massoterapia.Modelos.PlanoTratamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanoTratamentoRepository extends JpaRepository<PlanoTratamento, Long> {
    List<PlanoTratamento> findByClienteId(Long clienteId);
}
