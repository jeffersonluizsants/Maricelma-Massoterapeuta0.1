package com.maricelma.massoterapia.Serviço;

import com.maricelma.massoterapia.DTOs.DadosCadastroDespesasDTO;
import com.maricelma.massoterapia.DTOs.DadosDetalhamentoAgendamentoDTO;
import com.maricelma.massoterapia.DTOs.DadosDetalhamentoDespesasDTO;
import com.maricelma.massoterapia.DTOs.DashboardFinanceiroDTO;
import com.maricelma.massoterapia.Modelos.Agendamento;
import com.maricelma.massoterapia.Modelos.Despesas;
import com.maricelma.massoterapia.Repositorio.AgendamentoRepository;
import com.maricelma.massoterapia.Repositorio.DespesasRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class FinanceiroService {
    private final DespesasRepository despesasRepository;
    private final AgendamentoRepository agendamentoRepository;

    public FinanceiroService(DespesasRepository despesasRepository, AgendamentoRepository agendamentoRepository) {
        this.despesasRepository = despesasRepository;
        this.agendamentoRepository = agendamentoRepository;
    }

    @Transactional
    public DadosDetalhamentoDespesasDTO cadastrarDespesas(DadosCadastroDespesasDTO dados){
        var despesas = new Despesas();
        despesas.setDescricao(dados.descricao());
        despesas.setValor(dados.valor());
        despesas.setDataPagamento(dados.dataPagamento());
        despesas.setCategoria(dados.categoria());

        despesasRepository.save(despesas);
        return new DadosDetalhamentoDespesasDTO(despesas);
    }

    public List<DadosDetalhamentoDespesasDTO> listarDespesas(){
        return despesasRepository.findAll()
                .stream()
                .map(DadosDetalhamentoDespesasDTO::new)
                .toList();
    }

    public DashboardFinanceiroDTO obterDashboard(LocalDate inicio, LocalDate fim){
        BigDecimal faturamento = agendamentoRepository.somarFaturamentoPorPeriodo(inicio, fim);
        BigDecimal despesas = despesasRepository.somarDespesasPorPeriodo(inicio, fim);
        BigDecimal lucro = faturamento.subtract(despesas);

        Integer realizados = agendamentoRepository.contarPorStatusEPeriodo(Agendamento.StatusAgendamento.REALIZADO, inicio,fim);
        Integer pendentes = agendamentoRepository.contarPorStatusEPeriodo(Agendamento.StatusAgendamento.AGENDADO,inicio,fim);

        return new DashboardFinanceiroDTO(faturamento, despesas, lucro, realizados, pendentes);
    }
}
