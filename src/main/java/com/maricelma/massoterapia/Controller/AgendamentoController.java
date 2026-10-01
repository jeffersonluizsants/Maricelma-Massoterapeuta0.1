package com.maricelma.massoterapia.Controller;


import com.maricelma.massoterapia.DTOs.DadosAgendamentosDTO;
import com.maricelma.massoterapia.DTOs.DadosDetalhamentoAgendamentoDTO;
import com.maricelma.massoterapia.Serviço.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoAgendamentoDTO> agendar(@RequestBody @Valid DadosAgendamentosDTO dados){
        var dto = agendamentoService.agendar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoAgendamentoDTO>> listar() {
        var lista = agendamentoService.listar();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}/concluir")
    public ResponseEntity<DadosDetalhamentoAgendamentoDTO> concluir (@PathVariable Long id){
        var dto = agendamentoService.concluirAtendimento(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<DadosDetalhamentoAgendamentoDTO> cancelar(@PathVariable Long id){
        var dto = agendamentoService.cancelarAgendamento(id);
        return ResponseEntity.ok(dto);
    }
}
