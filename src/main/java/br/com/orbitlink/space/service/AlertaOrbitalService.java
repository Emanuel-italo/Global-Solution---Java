package br.com.orbitlink.space.service;

import br.com.orbitlink.space.dto.AlertaOrbitalRequest;
import br.com.orbitlink.space.dto.AlertaOrbitalResponse;
import br.com.orbitlink.space.entity.AlertaOrbital;
import br.com.orbitlink.space.entity.AtivoEspacial;
import br.com.orbitlink.space.repository.AlertaOrbitalDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlertaOrbitalService {

    private final AlertaOrbitalDao alertaOrbitalDao;
    private final AtivoEspacialService ativoEspacialService;

    public AlertaOrbitalService(AlertaOrbitalDao alertaOrbitalDao, AtivoEspacialService ativoEspacialService) {
        this.alertaOrbitalDao = alertaOrbitalDao;
        this.ativoEspacialService = ativoEspacialService;
    }

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

        return toResponse(alertaOrbitalDao.salvar(entidade));
    }

    @Transactional(readOnly = true)
    public AlertaOrbitalResponse buscarPorId(Long id) {
        return toResponse(alertaOrbitalDao.buscarPorIdOuFalhar(id));
    }

    @Transactional(readOnly = true)
    public List<AlertaOrbitalResponse> listarTodos() {
        return alertaOrbitalDao.listarTodos().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<AlertaOrbitalResponse> listarPorAtivoId(Long ativoId) {
        return alertaOrbitalDao.listarPorAtivoId(ativoId).stream().map(this::toResponse).toList();
    }

    @Transactional
    public AlertaOrbitalResponse atualizar(Long id, AlertaOrbitalRequest request) {
        AlertaOrbital entidade = alertaOrbitalDao.buscarPorIdOuFalhar(id);
        AtivoEspacial ativo = ativoEspacialService.buscarEntidadeOuFalhar(request.ativoId());

        entidade.setAtivoEspacial(ativo);
        entidade.setTipoAlerta(request.tipoAlerta());
        entidade.setCriticidade(request.criticidade());
        entidade.setMensagem(request.mensagem());
        entidade.setDataGeracao(request.dataGeracao());
        entidade.setResolvido(request.resolvido());

        return toResponse(alertaOrbitalDao.salvar(entidade));
    }

    @Transactional
    public void deletar(Long id) {
        alertaOrbitalDao.deletar(id);
    }

    private AlertaOrbitalResponse toResponse(AlertaOrbital entidade) {
        return new AlertaOrbitalResponse(
                entidade.getId(),
                entidade.getAtivoEspacial().getId(),
                entidade.getAtivoEspacial().getNome(),
                entidade.getTipoAlerta(),
                entidade.getCriticidade(),
                entidade.getMensagem(),
                entidade.getDataGeracao(),
                entidade.getResolvido()
        );
    }
}
