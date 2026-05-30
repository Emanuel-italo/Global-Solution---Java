package br.com.orbitlink.space.service;

import br.com.orbitlink.space.dto.RegistroTelemetriaRequest;
import br.com.orbitlink.space.dto.RegistroTelemetriaResponse;
import br.com.orbitlink.space.entity.AtivoEspacial;
import br.com.orbitlink.space.entity.RegistroTelemetria;
import br.com.orbitlink.space.exception.EntidadeNaoLocalizadaException;
import br.com.orbitlink.space.repository.RegistroTelemetriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistroTelemetriaService {

    private final RegistroTelemetriaRepository registroTelemetriaRepository;
    private final AtivoEspacialService ativoEspacialService;

    public RegistroTelemetriaService(RegistroTelemetriaRepository registroTelemetriaRepository, AtivoEspacialService ativoEspacialService) {
        this.registroTelemetriaRepository = registroTelemetriaRepository;
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

        return toResponse(registroTelemetriaRepository.save(entidade));
    }
        @Transactional(readOnly = true)
    public RegistroTelemetriaResponse buscarPorId(Long id) {
        return toResponse(buscarEntidadeOuFalhar(id));
    }
        @Transactional(readOnly = true)
    public List<RegistroTelemetriaResponse> listarTodos() {
        return registroTelemetriaRepository.buscarTodosComAtivo().stream().map(this::toResponse).toList();
    }

        @Transactional(readOnly = true)
    public List<RegistroTelemetriaResponse> listarPorAtivoId(Long ativoId) {
        return registroTelemetriaRepository.buscarPorAtivoId(ativoId).stream().map(this::toResponse).toList();
    }

    @Transactional
    public RegistroTelemetriaResponse atualizar(Long id, RegistroTelemetriaRequest request) {
        RegistroTelemetria entidade = buscarEntidadeOuFalhar(id);
        AtivoEspacial ativo = ativoEspacialService.buscarEntidadeOuFalhar(request.ativoId());

        entidade.setAtivoEspacial(ativo);
        entidade.setDataRegistro(request.dataRegistro());
        entidade.setClima(request.clima());
        entidade.setSinal(request.sinal());
        entidade.setLatitude(request.latitude());
        entidade.setLongitude(request.longitude());
        entidade.setObservacaoGps(gerarObservacaoGps(request));

        return toResponse(registroTelemetriaRepository.save(entidade));
    }

        @Transactional
    public void deletar(Long id) {
        RegistroTelemetria entidade = buscarEntidadeOuFalhar(id);
        registroTelemetriaRepository.delete(entidade);
    }

        private RegistroTelemetria buscarEntidadeOuFalhar(Long id) {
        return registroTelemetriaRepository.buscarComAtivoPorId(id)
                .orElseThrow(() -> new EntidadeNaoLocalizadaException("Registro de telemetria não encontrado com id " + id));
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