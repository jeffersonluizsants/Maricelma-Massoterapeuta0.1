package com.maricelma.massoterapia.Repositorio;

import com.maricelma.massoterapia.Modelos.PasswordResetToken;
import com.maricelma.massoterapia.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Void deleteByUsuario(Usuario usuario);
}
