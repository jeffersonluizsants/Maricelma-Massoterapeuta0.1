package com.maricelma.massoterapia.Serviço;

import com.maricelma.massoterapia.DTOs.DadosHorarioFuncionamentoDTO;
import com.maricelma.massoterapia.Modelos.HorarioFuncionamento;
import com.maricelma.massoterapia.Repositorio.HorarioFuncionamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class HorarioFuncionamentoService {
    private final HorarioFuncionamentoRepository horarioRepository;

    public HorarioFuncionamentoService(HorarioFuncionamentoRepository horarioRepository) {
        this.horarioRepository = horarioRepository;
    }

    @Transactional
    public DadosHorarioFuncionamentoDTO salvarOuAtualizar(DadosHorarioFuncionamentoDTO dados) {
        HorarioFuncionamento horario = horarioRepository.findByDiaSemana(dados.diaSemana())
                .orElse(new HorarioFuncionamento());
        horario.setDiaSemana(dados.diaSemana());
        horario.setHoraAbertura(dados.horaAbertura());
        horario.setHoraFechamento(dados.horaFechamento());
        horario.setAtivo(dados.ativo() != null ? dados.ativo() : true);

        horarioRepository.save(horario);
        return new DadosHorarioFuncionamentoDTO(horario);
    }

    public List<DadosHorarioFuncionamentoDTO> listar() {
        return horarioRepository.findAll()
                .stream()
                .map(DadosHorarioFuncionamentoDTO::new)
                .toList();
    }

    public boolean isHorarioValido(DayOfWeek dia, LocalTime inicio, LocalTime fim) {
        var horarioOpt = horarioRepository.findByDiaSemana(dia);
        if (horarioOpt.isEmpty()) return false;

        var horario = horarioOpt.get();
        if (!horario.getAtivo()) return false;

        return !inicio.isBefore(horario.getHoraAbertura()) && !fim.isAfter(horario.getHoraFechamento());
    }
}