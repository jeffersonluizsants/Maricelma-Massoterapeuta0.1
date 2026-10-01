package com.maricelma.massoterapia.Controller;


import com.maricelma.massoterapia.DTOs.DadosCadastroClienteDTO;
import com.maricelma.massoterapia.DTOs.DadosDetalhamentoClienteDTO;
import com.maricelma.massoterapia.Serviço.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoClienteDTO> cadastrar (@RequestBody @Valid DadosCadastroClienteDTO dados){
        var dto = clienteService.cadastrar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoClienteDTO>> listar(){
        var lista = clienteService.Listar();
        return ResponseEntity.ok(lista);
    }
}
