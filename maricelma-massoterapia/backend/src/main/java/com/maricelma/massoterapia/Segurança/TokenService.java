package com.maricelma.massoterapia.Segurança;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.maricelma.massoterapia.Modelos.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

@Value("${api.security.token.secret:minha-chave-super-segura-123}")
private String secret;


    public String gerarToken(Usuario usuer){
    try{
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return  JWT.create()
                .withIssuer("API MaricelmaMassoterapeuta")
                .withSubject(usuer.getUsername())
                .withExpiresAt(dataExpiracao())
                .sign(algorithm);
    }catch (JWTCreationException exception){
        throw new RuntimeException("Erro ao gerar o Token JWT", exception);
    }
}

public String validarToken(String tokenJWT){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("API MaricelmaMassoterapeuta")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        }catch (JWTCreationException exception){
            return "";
        }
}
private Instant dataExpiracao(){
   return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
}
}
