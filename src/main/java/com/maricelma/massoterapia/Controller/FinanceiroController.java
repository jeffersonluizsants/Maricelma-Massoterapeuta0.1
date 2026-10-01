package com.maricelma.massoterapia.Controller;

import com.maricelma.massoterapia.DTOs.DadosCadastroDespesasDTO;
import com.maricelma.massoterapia.DTOs.DadosDetalhamentoDespesasDTO;
import com.maricelma.massoterapia.DTOs.DashboardFinanceiroDTO;
import com.maricelma.massoterapia.Serviço.FinanceiroService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/financeiro")
public class FinanceiroController {

    private final FinanceiroService financeiroService;

    public FinanceiroController(FinanceiroService financeiroService) {
        this.financeiroService = financeiroService;
    }

    @PostMapping("/despesas")
    public ResponseEntity<DadosDetalhamentoDespesasDTO> cadastrarDespesa(@RequestBody @Valid DadosCadastroDespesasDTO dados){
        var dto = financeiroService.cadastrarDespesas(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @GetMapping("/despesas")
    public ResponseEntity<List<DadosDetalhamentoDespesasDTO>> listarDespesas(){
        var lista = financeiroService.listarDespesas();
        return ResponseEntity.ok(lista);
    }
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardFinanceiroDTO> obterDashboard(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fim
            ){
        var dto = financeiroService.obterDashboard(inicio, fim);
        return ResponseEntity.ok(dto);
    }

}
