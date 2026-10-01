package com.maricelma.massoterapia.Modelos;


import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "planos_tratamento")
@Data
public class PlanoTratamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    @Column(nullable = false)
    private Integer totalSessoes;

    @Column(nullable = false)
    private Integer sessoesRestantes;

    @Column
    private BigDecimal valorTotal;

    private LocalDateTime dataInicio = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Integer getTotalSessoes() {
        return totalSessoes;
    }

    public void setTotalSessoes(Integer totalSessoes) {
        this.totalSessoes = totalSessoes;
    }

    public Integer getSessoesRestantes() {
        return sessoesRestantes;
    }

    public void setSessoesRestantes(Integer sessoesRestantes) {
        this.sessoesRestantes = sessoesRestantes;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void abaterSessao(){
        if (this.sessoesRestantes <=0){
            throw new IllegalStateException("O plano já não possui mais sessões restantes disponíveis.");
        }
        this.sessoesRestantes--;
    }
}
