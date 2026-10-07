package com.maricelma.massoterapia.Controller;

import com.maricelma.massoterapia.DTOs.*;
import com.maricelma.massoterapia.Modelos.Usuario;
import com.maricelma.massoterapia.Repositorio.UsuarioRepository;
import com.maricelma.massoterapia.Segurança.TokenService;
import com.maricelma.massoterapia.Serviço.AutenticacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AutenticacaoService autenticacaoService;

    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, AutenticacaoService autenticacaoService) {
        this.manager = manager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping
    public ResponseEntity<DadosTokenJWTDTO> efetuarLogin(@RequestBody @Valid DadosAutenticacaoDTO dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWTDTO(tokenJWT));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrarUsuario(@RequestBody @Valid DadosCadastroDTO dados) {
        if (usuarioRepository.findByEmail(dados.email()) != null) {
            return ResponseEntity.badRequest().build();
        }
        Usuario usuario = new Usuario();
        usuario.setNome(dados.nome());
        usuario.setEmail(dados.email());
        usuario.setSenha(passwordEncoder.encode(dados.senha()));
        usuario.setRole(Usuario.Role.CLIENTE);

        usuarioRepository.save(usuario);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/esqueci-senha")
    public ResponseEntity<Void> solicitarRecuperacao(@RequestBody @Valid DadosEsqueciSenhaDTO dados){
        autenticacaoService.solicitarRecuperacaoSenha(dados.email());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<Void> redefinirSenha(@RequestBody @Valid DadosRedefinirSenhaDTO dados){
        autenticacaoService.redefinirSenha(dados.token(),dados.novaSenha());
        return ResponseEntity.ok().build();
    }
}