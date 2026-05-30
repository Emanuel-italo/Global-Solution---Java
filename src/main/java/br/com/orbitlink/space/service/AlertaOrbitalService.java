package br.com.orbitlink.space.service;

import br.com.orbitlink.space.dto.AlertaOrbitalRequest;
import br.com.orbitlink.space.dto.AlertaOrbitalResponse;
import br.com.orbitlink.space.entity.AlertaOrbital;
import br.com.orbitlink.space.entity.AtivoEspacial;
import br.com.orbitlink.space.exception.EntidadeNaoLocalizadaException;
import br.com.orbitlink.space.repository.AlertaOrbitalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlertaOrbitalService {

    private final AlertaOrbitalRepository alertaOrbitalRepository;
    private final AtivoEspacialService ativoEspacialService;

    public AlertaOrbitalService(AlertaOrbitalRepository alertaOrbitalRepository, AtivoEspacialService ativoEspacialService) {
        this.alertaOrbitalRepository = alertaOrbitalRepository;
        this.ativoEspacialService = ativoEspacialService;

    @Transactional
    public AlertaOrbitalResponse criar(AlertaOrbitalRequest request) {
        AtivoEspacial ativo = ativoEspacialService.buscarEntidadeOuFalhar(request.ativoId());

        AlertaOrbital entidade = new AlertaOrbital();
        entidade.setAtivoEspacial(ativo);
        entidade.setTipoAlerta(request.tipoAlerta());
        entidade.setCriticidade(request.criticidade());
        entidade.setMensagem(request.mensagem());
        entidade.setDataGeracao(request.dataGeracao());
        entidade.setResolvido(request.resolvido());

        return toResponse(alertaOrbitalRepository.save(entidade));

            @Transactional(readOnly = true)
    public AlertaOrbitalResponse buscarPorId(Long id) {
        return toResponse(buscarEntidadeOuFalhar(id));
    }

    @Transactional(readOnly = true)
    public List<AlertaOrbitalResponse> listarTodos() {
        return alertaOrbitalRepository.buscarTodosComAtivo().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<AlertaOrbitalResponse> listarPorAtivoId(Long ativoId) {
        return alertaOrbitalRepository.buscarPorAtivoId(ativoId).stream().map(this::toResponse).toList();
    }

        @Transactional
    public AlertaOrbitalResponse atualizar(Long id, AlertaOrbitalRequest request) {
        AlertaOrbital entidade = buscarEntidadeOuFalhar(id);
        AtivoEspacial ativo = ativoEspacialService.buscarEntidadeOuFalhar(request.ativoId());

        entidade.setAtivoEspacial(ativo);
        entidade.setTipoAlerta(request.tipoAlerta());
        entidade.setCriticidade(request.criticidade());
        entidade.setMensagem(request.mensagem());
        entidade.setDataGeracao(request.dataGeracao());
        entidade.setResolvido(request.resolvido());

        return toResponse(alertaOrbitalRepository.save(entidade));
    }
    
    }


    }
}