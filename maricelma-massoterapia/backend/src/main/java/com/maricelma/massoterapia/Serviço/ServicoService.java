package com.maricelma.massoterapia.Serviço;

import com.maricelma.massoterapia.DTOs.DadosCadastroServicoDTO;
import com.maricelma.massoterapia.DTOs.DadosDetalhamentoServicoDTO;
import com.maricelma.massoterapia.Modelos.Servico;
import com.maricelma.massoterapia.Repositorio.ServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicoService {
    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    @Transactional
    public DadosDetalhamentoServicoDTO cadastrar(DadosCadastroServicoDTO dados){
        var servico = new Servico();
        servico.setNome(dados.nome());
        servico.setDescricao(dados.descricao());
        servico.setDuracaoMinutos(dados.duracaoMinutos());
        servico.setValor(dados.valor());
        servico.setPossuiPlano(dados.possuiPlano() != null ? dados.possuiPlano() : false);

        servicoRepository.save(servico);
        return new DadosDetalhamentoServicoDTO(servico);
    }
    public List<DadosDetalhamentoServicoDTO> lisar(){
        return servicoRepository.findAll()
                .stream()
                .map(DadosDetalhamentoServicoDTO::new)
                .toList();
    }

}
