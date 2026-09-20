package com.maricelma.massoterapia.Controller;


import com.maricelma.massoterapia.DTOs.DadosCadastroServicoDTO;
import com.maricelma.massoterapia.DTOs.DadosDetalhamentoServicoDTO;
import com.maricelma.massoterapia.Serviço.ServicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoServicoDTO> cadastrar(@RequestBody @Valid DadosCadastroServicoDTO dados){
        var dto = servicoService.cadastrar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoServicoDTO>> listar(){
        var lista = servicoService.lisar();
        return ResponseEntity.ok(lista);
    }

}
