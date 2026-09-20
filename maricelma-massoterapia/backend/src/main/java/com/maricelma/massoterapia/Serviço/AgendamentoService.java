package com.maricelma.massoterapia.Serviço;

import com.maricelma.massoterapia.DTOs.DadosAgendamentosDTO;
import com.maricelma.massoterapia.DTOs.DadosDetalhamentoAgendamentoDTO;
import com.maricelma.massoterapia.Modelos.Agendamento;
import com.maricelma.massoterapia.Modelos.PlanoTratamento;
import com.maricelma.massoterapia.Repositorio.AgendamentoRepository;
import com.maricelma.massoterapia.Repositorio.ClienteRepository;
import com.maricelma.massoterapia.Repositorio.PlanoTratamentoRepository;
import com.maricelma.massoterapia.Repositorio.ServicoRepository;
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

    public AgendamentoService(AgendamentoRepository agendamentoRepository, ClienteRepository clienteRepository, ServicoRepository servicoRepository, PlanoTratamentoRepository planoTratamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;
        this.servicoRepository = servicoRepository;
        this.planoTratamentoRepository = planoTratamentoRepository;
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
