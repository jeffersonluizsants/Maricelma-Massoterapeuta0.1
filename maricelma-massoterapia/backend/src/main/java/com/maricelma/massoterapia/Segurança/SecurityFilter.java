package com.maricelma.massoterapia.Segurança;

import com.maricelma.massoterapia.Repositorio.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecurityFilter {
 private final TokenService tokenService;
 private final UsuarioRepository usuarioRepository;

    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        var tokenJWT = RecuperarToken(request);
        if (tokenJWT != null){
            var subject = tokenService.validarToken(tokenJWT);
            if (!subject.isEmpty()){
                var usuario = usuarioRepository.findByLogin(subject);
                var autenticacao = new UsernamePasswordAuthenticationToken(usuario,null);
                SecurityContextHolder.getContext().setAuthentication(autenticacao);
            }
        }
        filterChain.doFilter(request,response);
    }
    private String RecuperarToken(HttpServletRequest request){
        var autorizacaoCabeca = request.getHeader("Autorizado");
        if (autorizacaoCabeca!=null){
            return autorizacaoCabeca.replace("Bearer","");
        }
        return null;
    }
}

