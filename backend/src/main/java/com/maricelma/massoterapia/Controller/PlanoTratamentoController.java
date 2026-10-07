package com.maricelma.massoterapia.Controller;


import com.maricelma.massoterapia.DTOs.DadosCriarPLanoDTO;
import com.maricelma.massoterapia.DTOs.DadosDetalhamentoPlanoDTO;
import com.maricelma.massoterapia.Serviço.PlanoTratamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planos")
public class PlanoTratamentoController {

    private final PlanoTratamentoService planoService;

    public PlanoTratamentoController(PlanoTratamentoService planoService) {
        this.planoService = planoService;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoPlanoDTO> criarPlano(@RequestBody @Valid DadosCriarPLanoDTO dados){
        var dto = planoService.criarPlano(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);

    }
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<DadosDetalhamentoPlanoDTO>> listarPorCliente(@PathVariable Long clienteId){
        var lista = planoService.listarPorCliente(clienteId);
        return ResponseEntity.ok(lista);
    }
}
