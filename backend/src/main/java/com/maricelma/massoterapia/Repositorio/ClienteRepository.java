package com.maricelma.massoterapia.Repositorio;

import com.maricelma.massoterapia.Modelos.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    boolean existsByEmail(@NotBlank @Email String email);
}
