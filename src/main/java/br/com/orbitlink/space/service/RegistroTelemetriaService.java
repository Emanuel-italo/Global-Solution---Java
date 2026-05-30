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

}