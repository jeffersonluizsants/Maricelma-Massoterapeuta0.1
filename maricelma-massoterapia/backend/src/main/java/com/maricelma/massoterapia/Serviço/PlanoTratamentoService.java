package com.maricelma.massoterapia.Serviço;

import com.maricelma.massoterapia.DTOs.DadosCriarPLanoDTO;
import com.maricelma.massoterapia.DTOs.DadosDetalhamentoPlanoDTO;
import com.maricelma.massoterapia.Modelos.PlanoTratamento;
import com.maricelma.massoterapia.Repositorio.ClienteRepository;
import com.maricelma.massoterapia.Repositorio.PlanoTratamentoRepository;
import com.maricelma.massoterapia.Repositorio.ServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlanoTratamentoService {

    private final PlanoTratamentoRepository planoTratamentoRepository;
    private final ClienteRepository clienteRepository;
    private final ServicoRepository servicoRepository;

    public PlanoTratamentoService(PlanoTratamentoRepository planoTratamentoRepository, ClienteRepository clienteRepository, ServicoRepository servicoRepository) {
        this.planoTratamentoRepository = planoTratamentoRepository;
        this.clienteRepository = clienteRepository;
        this.servicoRepository = servicoRepository;
    }

    @Transactional
    public DadosDetalhamentoPlanoDTO criarPlano(DadosCriarPLanoDTO dados){
        var cliente = clienteRepository.findById(dados.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        var servico = servicoRepository.findById(dados.servicoId())
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));

        var plano = new PlanoTratamento();
        plano.setCliente(cliente);
        plano.setServico(servico);
        plano.setTotalSessoes(dados.totalSessoes());
        plano.setSessoesRestantes(dados.totalSessoes());
        plano.setValorTotal(dados.valorTotal());

        planoTratamentoRepository.save(plano);
        return new DadosDetalhamentoPlanoDTO(plano);
    }
    public List<DadosDetalhamentoPlanoDTO> listarPorCliente(Long clientId){
        return planoTratamentoRepository.findByClienteId(clientId)
                .stream()
                .map(DadosDetalhamentoPlanoDTO::new)
                .toList();
    }
}
