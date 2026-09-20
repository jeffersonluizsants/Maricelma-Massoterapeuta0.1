package com.maricelma.massoterapia.DTOs;

import com.maricelma.massoterapia.Modelos.Cliente;

import java.time.LocalDateTime;

public record DadosDetalhamentoClienteDTO(
        Long id,
        String nome,
        String telefone,
        String email,
        LocalDateTime dataCadastro
) {
    public DadosDetalhamentoClienteDTO(Cliente cliente) {
        this(cliente.getId(),cliente.getNome(),cliente.getTelefone(),cliente.getEmail(),cliente.getDataCadastro());
    }
}
