package com.maricelma.massoterapia.Serviço;


import com.maricelma.massoterapia.Modelos.PasswordResetToken;
import com.maricelma.massoterapia.Modelos.Usuario;
import com.maricelma.massoterapia.Repositorio.PasswordResetTokenRepository;
import com.maricelma.massoterapia.Repositorio.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AutenticacaoService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public AutenticacaoService(UsuarioRepository usuarioRepository, PasswordResetTokenRepository tokenRepository,PasswordEncoder passwordEncoder, EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username);
    }

    @Transactional
    public void solicitarRecuperacaoSenha(String email){
        var userDetails = usuarioRepository.findByEmail(email);
        if (userDetails instanceof Usuario usuario){
            tokenRepository.deleteByUsuario(usuario);

            String token = UUID.randomUUID().toString();
            var resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setUsuario(usuario);
            resetToken.setDataExpiracao(LocalDateTime.now().plusMinutes(30));

            tokenRepository.save(resetToken);

            try {
                emailService.enviarEmailRecuperacao(email, token);
            }catch (Exception e){
                System.out.println("TOKEN DE RECUPERAÇÃO GERADO (Log de Dev): " + token);
            }
        }
    }

    @Transactional
    public void redefinirSenha(String token, String novaSenha){
        var resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token incorreto ou não encontrado"));

        if (resetToken.isExpirado()){
            tokenRepository.delete(resetToken);
            throw new IllegalStateException("O token de redefinição expirou!");
        }
        Usuario usuario = resetToken.getUsuario();
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        tokenRepository.delete(resetToken);
    }
}
