package com.maricelma.massoterapia.Serviço;

import com.maricelma.massoterapia.DTOs.DadosAgendamentosDTO;
import com.maricelma.massoterapia.DTOs.DadosDetalhamentoAgendamentoDTO;
import com.maricelma.massoterapia.Modelos.Agendamento;
import com.maricelma.massoterapia.Modelos.PlanoTratamento;
import com.maricelma.massoterapia.Repositorio.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final ServicoRepository servicoRepository;
    private final PlanoTratamentoRepository planoTratamentoRepository;
    private final HorarioFuncionamentoService horarioFuncionamentoService;
    private final BloqueioAgendaService bloqueioAgendaService;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, ClienteRepository clienteRepository, ServicoRepository servicoRepository, PlanoTratamentoRepository planoTratamentoRepository, HorarioFuncionamentoService horarioFuncionamentoService, BloqueioAgendaService bloqueioAgendaService) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;
        this.servicoRepository = servicoRepository;
        this.planoTratamentoRepository = planoTratamentoRepository;
        this.horarioFuncionamentoService = horarioFuncionamentoService;
        this.bloqueioAgendaService = bloqueioAgendaService;
    }

    @Transactional
    public DadosDetalhamentoAgendamentoDTO agendar(DadosAgendamentosDTO dados){
        var clientes = clienteRepository.findById(dados.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        var servico = servicoRepository.findById(dados.servicoId())
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));

        LocalDateTime inicio = dados.dataHora();
        LocalDateTime fim = inicio.plusMinutes(servico.getDuracaoMinutos());

        var conflitos = agendamentoRepository.findConflitosDeHorario(servico.getId(), inicio, fim);
        if (!conflitos.isEmpty()){
            throw new IllegalStateException("Já existe um agendamento para este serviço no mesmo horário.");
        }
        PlanoTratamento planoTratamento = null;
        if (dados.planoId() != null){
            planoTratamento = planoTratamentoRepository.findById(dados.planoId())
                    .orElseThrow(() -> new IllegalArgumentException("Plano não encontrado"));
            if (planoTratamento.getSessoesRestantes() <= 0){
                throw new IllegalStateException("O plano selecionado não possui mais sessões disponíveis.");
            }
        }

        boolean dentroDoHoraio = horarioFuncionamentoService.isHorarioValido(
        inicio.getDayOfWeek(),
                inicio.toLocalTime(),
                fim.toLocalTime()
        );
        if (!dentroDoHoraio){
            throw new IllegalStateException("O agendamento está fora do horario de funcionamento!");
        }
        boolean block = bloqueioAgendaService.isHorarioBloqueado(inicio,fim);
        if (block){
            throw new IllegalStateException("Este horário está bloqueado na agenda do profissional.");
        }

        var agendamento = new Agendamento();
        agendamento.setCliente(clientes);
        agendamento.setServico(servico);
        agendamento.setPlano(planoTratamento);
        agendamento.setDataHora(dados.dataHora());
        agendamento.setStatus(Agendamento.StatusAgendamento.AGENDADO);

        agendamentoRepository.save(agendamento);
        return new DadosDetalhamentoAgendamentoDTO(agendamento);
    }

    @Transactional
    public DadosDetalhamentoAgendamentoDTO concluirAtendimento(Long id){
        var agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));
        if (agendamento.getStatus() != Agendamento.StatusAgendamento.AGENDADO){
            throw new IllegalStateException("Apenas agendamentos no status AGENDADO podem ser concluídos. ");
        }
        if (agendamento.getPlano() != null){
            PlanoTratamento plano = agendamento.getPlano();
            plano.abaterSessao();
            planoTratamentoRepository.save(plano);
        }
        agendamento.setStatus(Agendamento.StatusAgendamento.REALIZADO);
        agendamentoRepository.save(agendamento);

        return new DadosDetalhamentoAgendamentoDTO(agendamento);
    }
    @Transactional
    public DadosDetalhamentoAgendamentoDTO cancelarAgendamento(Long id){
        var agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));

        agendamento.setStatus(Agendamento.StatusAgendamento.CANCELADO);
        agendamentoRepository.save(agendamento);

        return new DadosDetalhamentoAgendamentoDTO(agendamento);
    }
    public List<DadosDetalhamentoAgendamentoDTO> listar(){
        return agendamentoRepository.findAll()
                .stream()
                .map(DadosDetalhamentoAgendamentoDTO::new)
                .toList();
    }
}
