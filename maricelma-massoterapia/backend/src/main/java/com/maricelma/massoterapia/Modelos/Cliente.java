package com.maricelma.massoterapia.Modelos;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
@Data
public class Cliente {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

@Column(nullable = false)
    private String nome;

@Column(nullable = false)
    private String telefone;
@Column(nullable = false, unique = true)
    private String email;

private LocalDateTime dataCadastro = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
