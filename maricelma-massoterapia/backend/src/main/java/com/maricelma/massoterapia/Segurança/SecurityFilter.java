package com.maricelma.massoterapia.Segurança;

import com.maricelma.massoterapia.Repositorio.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
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
                var usuario = usuarioRepository.findByEmail(subject);
                if (usuario != null) {
                    var autenticacao = new UsernamePasswordAuthenticationToken(usuario,null);
                    SecurityContextHolder.getContext().setAuthentication(autenticacao);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
    private String RecuperarToken(HttpServletRequest request){
        var authorizationHeader = request.getHeader("Autorization");
        if (authorizationHeader !=null && authorizationHeader.startsWith("Bearer")){
            return authorizationHeader.replace("Bearer","");
        }
        return null;
    }
}

