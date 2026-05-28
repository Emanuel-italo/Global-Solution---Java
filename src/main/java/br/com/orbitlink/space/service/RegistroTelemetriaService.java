package br.com.orbitlink.space.service;

import br.com.orbitlink.space.dto.RegistroTelemetriaRequest;
import br.com.orbitlink.space.dto.RegistroTelemetriaResponse;
import br.com.orbitlink.space.entity.AtivoEspacial;
import br.com.orbitlink.space.entity.RegistroTelemetria;
import br.com.orbitlink.space.repository.RegistroTelemetriaDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistroTelemetriaService {

    private final RegistroTelemetriaDao registroTelemetriaDao;
    private final AtivoEspacialService ativoEspacialService;

    public RegistroTelemetriaService(RegistroTelemetriaDao registroTelemetriaDao, AtivoEspacialService ativoEspacialService) {
        this.registroTelemetriaDao = registroTelemetriaDao;
        this.ativoEspacialService = ativoEspacialService;
    }

    @Transactional
    public RegistroTelemetriaResponse criar(RegistroTelemetriaRequest request) {
        AtivoEspacial ativo = ativoEspacialService.buscarEntidadeOuFalhar(request.ativoId());

        RegistroTelemetria entidade = new RegistroTelemetria();
        entidade.setAtivoEspacial(ativo);
        entidade.setDataRegistro(request.dataRegistro());
        entidade.setClima(request.clima());
        entidade.setSinal(request.sinal());
        entidade.setLatitude(request.latitude());
        entidade.setLongitude(request.longitude());
        entidade.setObservacaoGps(gerarObservacaoGps(request));

        return toResponse(registroTelemetriaDao.salvar(entidade));
    }

    @Transactional(readOnly = true)
    public RegistroTelemetriaResponse buscarPorId(Long id) {
        return toResponse(registroTelemetriaDao.buscarPorIdOuFalhar(id));
    }

    @Transactional(readOnly = true)
    public List<RegistroTelemetriaResponse> listarTodos() {
        return registroTelemetriaDao.listarTodos().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<RegistroTelemetriaResponse> listarPorAtivoId(Long ativoId) {
        return registroTelemetriaDao.listarPorAtivoId(ativoId).stream().map(this::toResponse).toList();
    }

    @Transactional
    public RegistroTelemetriaResponse atualizar(Long id, RegistroTelemetriaRequest request) {
        RegistroTelemetria entidade = registroTelemetriaDao.buscarPorIdOuFalhar(id);
        AtivoEspacial ativo = ativoEspacialService.buscarEntidadeOuFalhar(request.ativoId());

        entidade.setAtivoEspacial(ativo);
        entidade.setDataRegistro(request.dataRegistro());
        entidade.setClima(request.clima());
        entidade.setSinal(request.sinal());
        entidade.setLatitude(request.latitude());
        entidade.setLongitude(request.longitude());
        entidade.setObservacaoGps(gerarObservacaoGps(request));

        return toResponse(registroTelemetriaDao.salvar(entidade));
    }

    @Transactional
    public void deletar(Long id) {
        registroTelemetriaDao.deletar(id);
    }

    private String gerarObservacaoGps(RegistroTelemetriaRequest request) {
        String observacao = request.observacaoGps();
        if (observacao == null || observacao.isBlank()) {
            return String.format("GPS orbitando em %.6f / %.6f com raio operacional %.0f km",
                    request.latitude(), request.longitude(), request.distanciaOrbitalKm() == null ? 0.0 : request.distanciaOrbitalKm().doubleValue());
        }
        return observacao;
    }

    private RegistroTelemetriaResponse toResponse(RegistroTelemetria entidade) {
        return new RegistroTelemetriaResponse(
                entidade.getId(),
                entidade.getAtivoEspacial().getId(),
                entidade.getAtivoEspacial().getNome(),
                entidade.getDataRegistro(),
                entidade.getClima(),
                entidade.getSinal(),
                entidade.getLatitude(),
                entidade.getLongitude(),
                0,
                entidade.getObservacaoGps()
        );
    }
}
