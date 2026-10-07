package com.maricelma.massoterapia.Serviço;

import com.maricelma.massoterapia.DTOs.DadosCriarBloqueioDTO;
import com.maricelma.massoterapia.DTOs.DadosDetalhamentoBloqueioDTO;
import com.maricelma.massoterapia.Modelos.BloqueioAgenda;
import com.maricelma.massoterapia.Repositorio.BloqueioAgendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BloqueioAgendaService {
    private final BloqueioAgendaRepository bloqueioRepository;

    public BloqueioAgendaService(BloqueioAgendaRepository bloqueioRepository) {
        this.bloqueioRepository = bloqueioRepository;
    }

    @Transactional
    public DadosDetalhamentoBloqueioDTO criarBloqueio(DadosCriarBloqueioDTO dados){
        if (dados.dataHoraFIm().isBefore(dados.dataHoraInicio())){
            throw new IllegalArgumentException("A data/hora de fim do bloqueio deve ser posterior ao início.");
        }

        var block = new BloqueioAgenda();
        block.setMotivo(dados.motivo());
        block.setDataHoraInicio(dados.dataHoraInicio());
        block.setDataHoraFim(dados.dataHoraFIm());

        bloqueioRepository.save(block);
        return new DadosDetalhamentoBloqueioDTO(block);
    }
    public boolean isHorarioBloqueado(LocalDateTime inicio, LocalDateTime fim){
        var block = bloqueioRepository.findBloqueioConflitantes(inicio,fim);
        return !block.isEmpty();
    }
    public List<DadosDetalhamentoBloqueioDTO> listar(){

        return bloqueioRepository.findAll()
                .stream()
                .map(DadosDetalhamentoBloqueioDTO::new)
                .toList();
    }
    @Transactional
    public void removerBloqueio(Long id){
        if (!bloqueioRepository.existsById(id)){
            throw new IllegalArgumentException("Bloqueio não encontrado");
        }
        bloqueioRepository.deleteById(id);
    }
}
