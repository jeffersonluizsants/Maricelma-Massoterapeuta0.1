package com.maricelma.massoterapia.Serviço;

import com.maricelma.massoterapia.DTOs.DadosCadastroClienteDTO;
import com.maricelma.massoterapia.DTOs.DadosDetalhamentoClienteDTO;
import com.maricelma.massoterapia.Modelos.Cliente;
import com.maricelma.massoterapia.Repositorio.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public DadosDetalhamentoClienteDTO cadastrar(DadosCadastroClienteDTO dados){
        if (clienteRepository.existsByEmail(dados.email())){
            throw new IllegalArgumentException("Já existe um cliente cadastrado com esse email");
        }
        var cliente = new Cliente();
        cliente.setNome(dados.nome());
        cliente.setTelefone(dados.telefone());
        cliente.setEmail(dados.email());

        clienteRepository.save(cliente);
        return new DadosDetalhamentoClienteDTO(cliente);
    }
    public List<DadosDetalhamentoClienteDTO> Listar(){
        return clienteRepository.findAll()
                .stream()
                .map(DadosDetalhamentoClienteDTO::new)
                .toList();
    }
}
