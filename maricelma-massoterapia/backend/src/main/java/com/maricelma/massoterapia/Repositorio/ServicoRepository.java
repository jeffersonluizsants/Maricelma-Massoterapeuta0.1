package com.maricelma.massoterapia.Repositorio;

import com.maricelma.massoterapia.Modelos.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    boolean existsByEmail(String email);
}
